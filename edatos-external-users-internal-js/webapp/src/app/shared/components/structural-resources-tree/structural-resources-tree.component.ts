import { Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output, ViewChild } from '@angular/core';
import { MultiLanguageInputComponent } from '@app/shared/components/multi-language-input/multi-language-input.component';
import { Category, Favorite, InternationalString } from '@app/shared/model';
import { CategoryService, LanguageService } from '@app/shared/service';
import { TranslateService } from '@ngx-translate/core';
import { ArteAlertService } from 'arte-ng/services';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';
import { shareReplay } from 'rxjs/operators';

export type Mode = 'view' | 'select' | 'edit';

export interface CategoryTreeNode extends TreeNode {
    data: Category;
    children?: CategoryTreeNode[];
    edit: boolean;
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

    public resources: CategoryTreeNode[];
    public selectedResources: CategoryTreeNode[] = [];
    public selectionMode: 'checkbox' | 'single' | 'multiple' = 'checkbox';
    public enableDragAndDrop = false;
    public allowedLanguages: Observable<string[]>;

    public selection = [];

    private readonly mainLanguageCode: string;
    private tree: Category[] = [];
    private iterableDiffer: IterableDiffer<Favorite>;
    private nodeList: CategoryTreeNode[] = [];

    constructor(
        private alertService: ArteAlertService,
        private categoryService: CategoryService,
        private translateService: TranslateService,
        private iterableDiffers: IterableDiffers,
        private languageService: LanguageService
    ) {
        this.mainLanguageCode = this.translateService.getDefaultLang();
        this.iterableDiffer = iterableDiffers.find([]).create();
        // sharedReplay RxJS operator allows for caching the http response, since
        // languages are not that frequently updated (and can be updated just by
        // reloading the page, the cache just avoids unnecessary requests every time
        // a multi-language input component is opened).
        this.allowedLanguages = languageService.getAllowed().pipe(shareReplay({ bufferSize: 1, refCount: true }));
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
        this.categoryService.getTree().subscribe((categories) => {
            this.tree = this.sort(categories);
            this.createTree();
        });
    }

    public createTree() {
        this.categoryListToCategoryTree(this.tree).subscribe((treeNodes) => {
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
                selectable: !this.disabled,
                edit: false,
            };
            parent.children.push(node);
            this.nodeList.push(node);
            this.enableNodeEdit(node);
        } else {
            // TODO(EDATOS-3357): What happens when the tree is empty?
        }
    }

    public deleteNode(node: CategoryTreeNode) {
        this.nodeList.splice(this.nodeList.indexOf(node));
        node.parent?.children?.splice(node.parent?.children.indexOf(node));
    }

    public saveNodeName(node: CategoryTreeNode, name: InternationalString) {
        node.data.name = name;
        node.label = name.getLocalisedLabel(this.mainLanguageCode);
        this.disableNodeEdit(node);
    }

    public disableNodeEdit(node: CategoryTreeNode) {
        if (node.data.name.isEmptyOrBlank()) {
            this.deleteNode(node);
        }
        node.edit = false;
    }

    public enableNodeEdit(node: CategoryTreeNode) {
        node.edit = true;
    }

    private sort(categories: Category[]): Category[] {
        categories.sort((a, b) => (a.name.getLocalisedLabel(this.mainLanguageCode)! < b.name.getLocalisedLabel(this.mainLanguageCode)! ? -1 : 1));
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
            label: category.name.getLocalisedLabel(this.mainLanguageCode),
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            children,
            data: category,
            selectable: !this.disabled,
            edit: false,
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

    private setLoadingNode(node: CategoryTreeNode) {
        node.icon = 'fa fa-spinner fa-spin';
        node.selectable = false;
        if (node.children) {
            for (const child of node.children) {
                this.setLoadingNode(child);
            }
        }
    }

    private unsetLoadingNode(node: CategoryTreeNode) {
        node.icon = null;
        node.selectable = !this.disabled;
        if (node.children) {
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
