import { ChangeDetectorRef, Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output, ViewChild } from '@angular/core';
import { MultiLanguageInputComponent } from '@app/shared/components/multi-language-input';
import { Category, ExternalCategory, Favorite, InternationalString } from '@app/shared/model';
import { CategoryService, LanguageService } from '@app/shared/service';
import { TranslateService } from '@ngx-translate/core';
import { ArteAlertService } from 'arte-ng/services';
import * as _ from 'lodash';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';
import { finalize, shareReplay } from 'rxjs/operators';

/**
 * @see StructuralResourcesTreeComponent#mode
 */
export type Mode = 'view' | 'select' | 'edit';

export interface CategoryTreeNode extends TreeNode {
    data: Category;
    parent?: CategoryTreeNode;
    children?: CategoryTreeNode[];
    editMode: 'remap' | 'name' | null;
    makingRequest?: boolean;
}

@Component({
    selector: 'app-structural-resources-tree',
    templateUrl: './structural-resources-tree.component.html',
    styleUrls: ['./structural-resources-tree.component.scss'],
})
export class StructuralResourcesTreeComponent implements OnInit, DoCheck {
    /**
     * The list of favorites. The selection of the tree is automatically updated when favorites
     * are added or removed.
     */
    @Input()
    public favorites?: Favorite[];

    /**
     * If true, the elements on the tree become unselectable. On 'view' mode is true by default.
     *
     * @see StructuralResourcesTreeComponent#mode
     */
    @Input()
    public disabled = false;

    /**
     * Mode of the component.
     *
     * If set to 'select', the tree show checkboxes next to each element.
     * {@link StructuralResourcesTreeComponent#favorites} becomes necessary since it's where
     * the selected favorites are synchronized. This is the default mode.
     *
     * If set to 'view', the tree shows the structural resources without any checkbox and
     * they are not selectable. {@link StructuralResourcesTreeComponent#favorites} is not
     * required.
     *
     * If set to 'edit', the tree allows to create, delete, and move around nodes. This
     * mode it's meant to map the elements of the tree to categories and operations.
     */
    @Input()
    public mode: Mode = 'select';

    /**
     * Emits an event when an element of the tree is selected.
     */
    @Output()
    public onResourceSelect = new EventEmitter<Category>();

    /**
     * Emits an event when an element of the tree is unselected.
     */
    @Output()
    public onResourceUnselect = new EventEmitter<Category>();

    @ViewChild(MultiLanguageInputComponent)
    public languageInputComponent: MultiLanguageInputComponent;

    public tree: CategoryTreeNode[];
    public selectedResources: CategoryTreeNode[] = [];
    public selectionMode: 'checkbox' | 'single' | 'multiple' = 'checkbox';
    public enableDragAndDrop = false;
    public allowedLanguages: string[];
    public externalCategories: ExternalCategory[];
    public loading = false;

    private iterableDiffer: IterableDiffer<Favorite>;
    private nodeList: CategoryTreeNode[] = [];

    constructor(
        private alertService: ArteAlertService,
        private categoryService: CategoryService,
        private translateService: TranslateService,
        private iterableDiffers: IterableDiffers,
        private languageService: LanguageService,
        private _changeDetectorRef: ChangeDetectorRef
    ) {
        this.translateService.onLangChange.subscribe(() => {
            this._changeDetectorRef.detectChanges();
        });
        this.iterableDiffer = iterableDiffers.find([]).create();
        // sharedReplay RxJS operator allows for caching the http response, since
        // languages are not that frequently updated (and can be updated just by
        // reloading the page, the cache just avoids unnecessary requests every time
        // a multi-language input component is opened).
        this.languageService
            .getAllowed()
            .pipe(shareReplay({ bufferSize: 1, refCount: true }))
            .subscribe((languages) => (this.allowedLanguages = languages));
        this.categoryService
            .getExternalCategories()
            .pipe(shareReplay({ bufferSize: 1, refCount: true }))
            .subscribe((categories) => (this.externalCategories = categories));
    }

    public ngDoCheck(): void {
        // On the use of iterableDiffer, see https://stackoverflow.com/a/42962723/7611990.
        // Angular data-binding checking system does not check if the array has new elements, or
        // if it deleted them. When the user selects and unselect favorites, the array changes and
        // we must detect those changes to update the element selection on the tree.
        const changes = this.iterableDiffer.diff(this.favorites);
        if (changes) {
            this.updateSelection();
        }
    }

    public ngOnInit(): void {
        this.setMode();
        this.categoryService.getTree().subscribe((tree) => {
            this.createTree(this.sort(tree));
        });
    }

    public createTree(tree: Category[]) {
        this.nodeList = [];
        this.categoryListToCategoryTree(tree).subscribe((treeNodes) => {
            this.tree = treeNodes;
        });
    }

    public updateSelection() {
        this.selectedResources = [];
        for (const node of this.nodeList) {
            this.unsetLoadingNode(node);
            if (this.isFavorite(node.data)) {
                this.selectedResources.push(node);
            }
        }
    }

    public onSelect(treeNode: CategoryTreeNode) {
        this.setLoadingNode(treeNode);
        this.onResourceSelect.emit(treeNode.data);
    }

    public onUnselect(treeNode: CategoryTreeNode) {
        this.setLoadingNode(treeNode);
        this.onResourceUnselect.emit(treeNode.data);
    }

    public addNode(parent?: CategoryTreeNode) {
        const node: CategoryTreeNode = {
            label: '',
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            data: new Category(),
            children: [],
            selectable: !this.disabled,
            editMode: null,
        };
        if (parent) {
            if (!parent.children) {
                parent.children = [];
            }
            parent.children.push(node);
            parent.data.children.push(node.data);
        } else {
            this.tree.push(node);
        }
        this.nodeList.push(node);
        this.editNodeName(node);
    }

    public deleteNode(node: CategoryTreeNode) {
        _.pull(this.nodeList, node);
        if (node.parent) {
            _.pull(node.parent.children, node);
            _.pull(node.parent.data?.children, node.data);
        } else {
            _.pull(this.tree, node);
        }
        if (node.data.id) {
            this.categoryService
                .deleteCategory(node.data.id)
                .pipe(finalize(() => this.unsetLoadingNode(node)))
                .subscribe(() => this.ngOnInit());
        }
    }

    public saveNodeName(node: CategoryTreeNode, name: InternationalString) {
        const oldName = node.data.name;
        this.setNodeNameFromInternationalString(node, name);
        this.disableNodeEdit(node);
        this.setLoadingNode(node, false);

        const saveOrUpdateCategory = node.data.id != null ? this.categoryService.createCategory : this.categoryService.updateCategory;

        saveOrUpdateCategory(node.data, node.parent?.data)
            .pipe(finalize(() => this.unsetLoadingNode(node, false)))
            .subscribe({
                next: (category) => (node.data = category),
                error: () => this.setNodeNameFromInternationalString(node, oldName),
            });
    }

    public disableNodeEdit(node: CategoryTreeNode) {
        if (node.data.name.isEmptyOrBlank()) {
            this.deleteNode(node);
        }
        node.editMode = null;
    }

    public editNodeName(node: CategoryTreeNode) {
        node.editMode = 'name';
    }

    public remapNode(node: CategoryTreeNode) {
        node.editMode = 'remap';
    }

    public saveRemap(node: CategoryTreeNode, selectedResources: ExternalCategory[]) {
        const oldSelectedResources = node.data.resources;
        node.data.resources = selectedResources;
        node.editMode = null;
        this.setLoadingNode(node, false);
        this.categoryService
            .updateCategory(node.data, node.parent?.data)
            .pipe(finalize(() => this.unsetLoadingNode(node, false)))
            .subscribe({
                next: (category) => (node.data = category),
                error: () => (node.data.resources = oldSelectedResources),
            });
    }

    public onNodeDrop(event: { dragNode: CategoryTreeNode; dropNode: CategoryTreeNode } & ({ index: number } | { dropIndex: number })) {
        // This function is executed *before* the tree is reorganized, meaning we can't rely on the node information
        // itself to know who is it's parent.
        //
        // By the way the PrimeNG tree works, we receive two main elements: the node that was selected and dragged
        // and the drop node. The dropNode can be a parent or a sibling depending of the field present in the event obj:
        //  - If the event object contains an 'index' property, dropNode it's the parent of the dragged node.
        //  - If the event object contains a 'dropIndex' property, dropNode it's a sibling of the dragged node, and the
        //    parent of dropNode will be the same as ours.
        //
        // In both cases, 'index' and 'dropIndex' represent the position where the dragged node is in the list.
        let parent: CategoryTreeNode;
        if (event.hasOwnProperty('index')) {
            parent = event.dropNode;
        } else {
            parent = event.dropNode.parent;
        }
        parent?.data.children.push(event.dragNode.data);
        _.pull(event.dragNode.parent?.data.children, event.dragNode.data);
        this.loading = true;
        this.categoryService
            .updateTree(this.tree.map((node) => node.data))
            .pipe(finalize(() => (this.loading = false)))
            .subscribe((tree) => this.createTree(this.sort(tree)));
    }

    private setNodeNameFromInternationalString(node: CategoryTreeNode, name: InternationalString) {
        node.data.name = name;
        node.label = name.val;
    }

    private sort(categories: Category[]): Category[] {
        categories.sort((a, b) => (a.name.val! < b.name.val! ? -1 : 1));
        for (const element of categories) {
            this.sort(element.children);
        }
        return categories;
    }

    private categoryListToCategoryTree(categories: Category[]): Observable<CategoryTreeNode[]> {
        return of(
            categories?.map((category) => {
                const children: CategoryTreeNode[] = [];
                this.categoryListToCategoryTree(category.children).subscribe((treeNodes) => {
                    children.push(...treeNodes);
                });
                return this.categoryToCategoryTreeNode(category, children);
            })
        );
    }

    private categoryToCategoryTreeNode(category: Category, children: CategoryTreeNode[]): CategoryTreeNode {
        const node: CategoryTreeNode = {
            label: category.name.val,
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            children,
            data: category,
            selectable: !this.disabled,
            editMode: null,
            makingRequest: false,
        };

        if (this.isFavorite(category)) {
            this.selectedResources.push(node);
        }
        this.nodeList.push(node);

        return node;
    }

    private isFavorite(category: Category): boolean {
        return this.favorites?.some((favorite) => favorite.category.id === category.id) ?? false;
    }

    private setLoadingNode(node: CategoryTreeNode, setLoadingChildren = true) {
        node.icon = 'fa fa-spinner fa-spin';
        node.selectable = false;
        if (node.children && setLoadingChildren) {
            for (const child of node.children) {
                this.setLoadingNode(child);
            }
        }
    }

    private unsetLoadingNode(node: CategoryTreeNode, setLoadingChildren = true) {
        node.icon = null;
        node.selectable = !this.disabled;
        if (node.children && setLoadingChildren) {
            for (const child of node.children) {
                this.unsetLoadingNode(child);
            }
        }
    }

    private setMode() {
        switch (this.mode) {
            case 'view':
                this.selectionMode = 'single';
                this.disabled = true;
                break;
            case 'select':
                this.selectionMode = 'checkbox';
                break;
            case 'edit':
                this.selectionMode = 'single';
                this.enableDragAndDrop = true;
                break;
        }
    }
}
