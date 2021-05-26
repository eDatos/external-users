import { ChangeDetectorRef, Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { MultiLanguageInputComponent } from '@app/shared/components/multi-language-input';
import { Category, ExternalCategory, ExternalOperation, Favorite, InternationalString } from '@app/shared/model';
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
export type Mode = 'view' | 'select' | 'simpleSelect' | 'edit';
export type FavoriteResource = Category | ExternalOperation;

export interface CategoryTreeNode extends TreeNode {
    data: FavoriteResource;
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
export class StructuralResourcesTreeComponent implements OnInit, DoCheck, OnChanges {
    /**
     * The list of favorites. The selection of the tree is automatically updated when favorites
     * are added or removed. Only works in 'select' {@link mode}.
     */
    @Input()
    public favorites: Favorite[] = [];

    /**
     * List of category nodes, which has been selected in a previous instance. Used to preselect categories on the tree
     */
    @Input()
    public preselectedResources?: CategoryTreeNode[];

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
     * they are not selectable.
     *
     * If set to 'edit', the tree allows to create, delete, and move around nodes. This
     * mode it's meant to map the elements of the tree to categories and operations.
     */
    @Input()
    public mode: Mode = 'select';

    /**
     * Determines whether the selection of a parent node propagates down to its descendants, and
     * whether the selection of all children propagates up to its parents. Only works in 'select' {@link mode}.
     */
    @Input()
    public propagateSelection: boolean;

    /**
     * Emits an event when an element of the tree is selected. Only works in 'select' {@link mode}.
     */
    @Output()
    public onResourceSelect = new EventEmitter<CategoryTreeNode>();

    /**
     * Emits an event when an element of the tree is unselected. Only works in 'select' {@link mode}.
     */
    @Output()
    public onResourceUnselect = new EventEmitter<CategoryTreeNode>();

    @ViewChild(MultiLanguageInputComponent)
    public languageInputComponent: MultiLanguageInputComponent;

    public tree: CategoryTreeNode[];
    public selectedResources: CategoryTreeNode[] = [];
    public selectionMode: 'checkbox' | 'single' | 'multiple' = 'checkbox';
    public enableDragAndDrop = false;
    public allowedLanguages: string[];
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
    }

    public ngOnChanges(changes: SimpleChanges) {
        if (changes.disabled) {
            for (const node of this.nodeList) {
                node.selectable = !this.disabled;
            }
        }
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
            this.createTree(tree);
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
        if (this.onResourceSelect.observers.length > 0) {
            this.onResourceSelect.emit(treeNode);
        }
    }

    public onUnselect(treeNode: CategoryTreeNode) {
        if (this.onResourceUnselect.observers.length) {
            this.onResourceUnselect.emit(treeNode);
        }
    }

    public addNode(parent?: CategoryTreeNode) {
        const category = new Category();
        const node: CategoryTreeNode = {
            label: '',
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            data: category,
            children: [],
            selectable: !this.disabled,
            editMode: null,
        };
        if (parent) {
            if (!parent.children) {
                parent.children = [];
            }
            parent.children.push(node);
            category.index = parent.children.length;
            if (parent.data instanceof Category) {
                parent.data.children.push(category);
            }
        } else {
            category.index = this.tree.length;
            this.tree.push(node);
        }
        this.nodeList.push(node);
        this.editNodeName(node);
    }

    public deleteNode(node: CategoryTreeNode) {
        _.pull(this.nodeList, node);
        if (node.parent) {
            _.pull(node.parent.children!, node);
            if (node.parent.data instanceof Category) {
                _.pull(node.parent.data?.children, node.data);
            }
        } else {
            _.pull(this.tree, node);
        }
        if (node.data.id) {
            this.loading = true;
            this.categoryService
                .deleteCategory(node.data.id)
                .pipe(
                    finalize(() => {
                        this.ngOnInit();
                        this.loading = false;
                    })
                )
                .subscribe();
        }
    }

    public saveNodeName(node: CategoryTreeNode, name: InternationalString) {
        this.setNodeNameFromInternationalString(node, name);
        this.disableNodeEdit(node);
        this.updateTree();
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
        if (node.data instanceof Category) {
            node.data.externalCategories = selectedResources;
        }
        node.editMode = null;
        this.updateTree();
    }

    public onNodeDrop(event: { dragNode: CategoryTreeNode; dropNode: CategoryTreeNode } & ({ index: number } | { dropIndex: number })) {
        this.loading = true;
        this.reconstructTree(this.tree);
        this.categoryService
            .updateTree(this.tree.map((node) => node.data as Category))
            .pipe(finalize(() => (this.loading = false)))
            .subscribe({
                next: (tree) => this.createTree(tree),
                error: () => this.ngOnInit(),
            });
    }

    public getDisabledExternalCategories(selectedCategory: Category): ExternalCategory[] {
        const externalCategoriesAlreadySelected: ExternalCategory[] = [];
        for (const node of this.nodeList) {
            if (node.data.id !== selectedCategory.id) {
                externalCategoriesAlreadySelected.push(...(node.data as Category).externalCategories);
            }
        }
        return externalCategoriesAlreadySelected;
    }

    private updateTree() {
        this.categoryService
            .updateTree(this.tree.map((node) => node.data as Category))
            .pipe(finalize(() => (this.loading = false)))
            .subscribe({
                next: (tree) => this.createTree(tree),
                error: () => this.ngOnInit(),
            });
    }

    private setNodeNameFromInternationalString(node: CategoryTreeNode, name: InternationalString) {
        node.data.name = name;
        node.label = name.val;
    }

    private categoryListToCategoryTree(categories: Category[]): Observable<CategoryTreeNode[]> {
        return of(
            categories
                ?.map((category) => {
                    const children: CategoryTreeNode[] = [];
                    this.categoryListToCategoryTree(category.children).subscribe((treeNodes) => {
                        children.push(...treeNodes);
                    });
                    if (this.mode !== 'edit') {
                        for (const externalOperation of category.externalOperations) {
                            children.push(this.externalOperationToTreeNode(externalOperation));
                        }
                    }
                    return this.categoryToCategoryTreeNode(category, children);
                })
                .sort((a, b) => {
                    if (a.data instanceof Category && b.data instanceof Category) {
                        return a.data.index < b.data.index ? -1 : 1;
                    } else {
                        return a.data.name.val! < b.data.name.val! ? -1 : 1;
                    }
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

        if (this.isFavorite(category) || this.isPreselected(category)) {
            this.selectedResources.push(node);
        }
        this.nodeList.push(node);

        return node;
    }

    private isFavorite(resource: Category | ExternalOperation): boolean {
        return this.favorites?.some((favorite) => favorite.resource?.id === resource.id && favorite.resource?.favoriteType === resource.favoriteType) ?? false;
    }

    private isPreselected(category: Category): boolean {
        return this.preselectedResources?.some((resource) => resource.data.id === category.id) ?? false;
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
        if (node.data.favoriteType === 'externalOperation') {
            node.icon = 'fa fa-table';
        } else {
            node.icon = null;
        }
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
            case 'simpleSelect':
                this.selectionMode = 'checkbox';
                this.propagateSelection = false;
                break;
            case 'select':
                this.selectionMode = 'checkbox';
                this.propagateSelection = true;
                break;
            case 'edit':
                this.selectionMode = 'single';
                this.enableDragAndDrop = true;
                break;
        }
    }

    private reconstructTree(tree: CategoryTreeNode[]) {
        for (const [index, node] of tree.entries()) {
            if (node.data instanceof Category) {
                node.data.index = index;
                node.data.children = node.children!.filter((child) => child.data instanceof Category).map((child) => child.data as Category);
            }
        }
        for (const node of tree) {
            this.reconstructTree(node.children!);
        }
    }

    private externalOperationToTreeNode(externalOperation: ExternalOperation): CategoryTreeNode {
        const node = {
            label: externalOperation.name.val,
            icon: 'fa fa-table',
            data: externalOperation,
            selectable: !this.disabled,
            editMode: null,
            makingRequest: false,
        };

        if (this.isFavorite(externalOperation)) {
            this.selectedResources.push(node);
        }
        this.nodeList.push(node);

        return node;
    }
}
