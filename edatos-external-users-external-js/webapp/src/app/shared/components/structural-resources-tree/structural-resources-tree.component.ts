import { ChangeDetectorRef, Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output, ViewChild } from '@angular/core';
import { Category, Favorite, InternationalString, Operation, StructuralResourcesTree } from '@app/shared/model';
import { ExternalCategory } from '@app/shared/model/external-item.model';
import { CategoryService } from '@app/shared/service/category/category.service';
import { TranslateService } from '@ngx-translate/core';
import { ArteAlertService } from 'arte-ng/services';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';
import { MultiLanguageInputComponent } from '../multi-language-input/multi-language-input.component';
import { shareReplay } from 'rxjs/operators';
import { LanguageService } from '@app/shared/service';
import * as _ from 'lodash';

export type Mode = 'view' | 'select' | 'edit';

export interface CategoryTreeNode extends TreeNode {
    data: Category;
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

    public resources: CategoryTreeNode[];
    public selectedResources: CategoryTreeNode[] = [];
    public selectionMode: 'checkbox' | 'single' | 'multiple' = 'checkbox';
    public enableDragAndDrop = false;
    public allowedLanguages: string[];
    public externalCategories: ExternalCategory[];

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
        this.languageService
            .getAllowed()
            .pipe(shareReplay({ bufferSize: 1, refCount: true }))
            .subscribe((languages) => (this.allowedLanguages = languages));
        this.categoryService
            .getExternal()
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
        this.categoryListToCategoryTree(tree).subscribe((treeNodes) => {
            this.resources = treeNodes;
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
        if (parent) {
            if (!parent.children) {
                parent.children = [];
            }
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
            parent.children.push(node);
            parent.data.children.push(node.data);
            this.nodeList.push(node);
            this.editNodeName(node);
        } else {
            // TODO(EDATOS-3357): What happens when the tree is empty?
        }
    }

    public deleteNode(node: CategoryTreeNode) {
        _.pull(this.nodeList, node);
        _.pull(node.parent?.children, node);
        _.pull(node.parent?.data?.children, node.data);
    }

    public saveNodeName(node: CategoryTreeNode, name: InternationalString) {
        node.data.name = name;
        node.label = name.val;
        this.disableNodeEdit(node);
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
        node.data.resources = selectedResources;
        node.editMode = null;
    }

    public save(): Observable<Category[]> {
        return this.categoryService.updateTree(this.resources.map((node) => node.data));
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
        if (event.hasOwnProperty('index')) {
            _.pull(event.dragNode.parent?.data?.children, event.dragNode.data);
            event.dropNode.data?.children?.push(event.dragNode.data);
        } else {
            _.pull(event.dragNode.parent?.data?.children, event.dragNode.data);
            event.dropNode.parent?.data?.children?.push(event.dragNode.data);
        }
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
