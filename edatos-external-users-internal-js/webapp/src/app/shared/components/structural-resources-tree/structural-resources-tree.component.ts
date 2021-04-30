import { Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output } from '@angular/core';
import { Category, Favorite } from '@app/shared/model';
import { CategoryService } from '@app/shared/service/category/category.service';
import { TranslateService } from '@ngx-translate/core';
import { ArteAlertService } from 'arte-ng/services';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';

export type Mode = 'view' | 'select' | 'edit';

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

    public resources: TreeNode[];
    public selectedResources: TreeNode[] = [];
    public selectionMode: 'checkbox' | 'single' | 'multiple' = 'checkbox';
    public enableDragAndDrop = false;

    private readonly mainLanguageCode: string;
    private tree: Category[] = [];
    private iterableDiffer: IterableDiffer<Favorite>;
    private nodeList: TreeNode[] = [];

    constructor(
        private alertService: ArteAlertService,
        private categoryService: CategoryService,
        private translateService: TranslateService,
        private iterableDiffers: IterableDiffers
    ) {
        this.mainLanguageCode = this.translateService.getDefaultLang();
        this.iterableDiffer = iterableDiffers.find([]).create();
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
        this.categoryListToTreeNode(this.tree).subscribe((treeNodes) => {
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

    public onSelect(treeNode: TreeNode) {
        this.setLoadingNode(treeNode);
        this.onResourceSelect.emit(treeNode.data);
    }

    public onUnselect(treeNode: TreeNode) {
        this.setLoadingNode(treeNode);
        this.onResourceUnselect.emit(treeNode.data);
    }

    public addNode(parent?: TreeNode) {
        if (parent) {
            if (!parent.children) {
                parent.children = [];
            }
            const node: TreeNode = {
                label: '',
                collapsedIcon: 'fa fa-folder',
                expandedIcon: 'fa fa-folder-open',
                expanded: true,
                data: new Category(),
                selectable: !this.disabled,
            };
            parent.children.push(node);
            this.nodeList.push(node);
            this.enableNodeEdit(node);
        } else {
            // TODO(EDATOS-3357): What happens when the tree is empty?
        }
    }

    public deleteNode(node: TreeNode) {
        this.nodeList.splice(this.nodeList.indexOf(node));
        node.parent?.children?.splice(node.parent?.children.indexOf(node));
    }

    public saveNodeName(node: TreeNode, content: string) {
        node.label = content;
        this.disableNodeEdit(node);
    }

    public disableNodeEdit(node: TreeNode & { edit?: boolean }) {
        if (node.label?.trim().length === 0) {
            this.deleteNode(node);
        }
        node.edit = false;
    }

    public enableNodeEdit(node: TreeNode & { edit?: boolean }) {
        node.edit = true;
    }

    public setInputLength(inputRef: HTMLInputElement) {
        // See https://css-tricks.com/auto-growing-inputs-textareas/
        if (inputRef.parentElement) {
            inputRef.parentElement.dataset.value = inputRef.value;
        }
    }

    private sort(categories: Category[]): Category[] {
        categories.sort((a, b) => (a.name.getLocalisedLabel(this.mainLanguageCode)! < b.name.getLocalisedLabel(this.mainLanguageCode)! ? -1 : 1));
        for (const element of categories) {
            this.sort(element.children);
        }
        return categories;
    }

    private categoryListToTreeNode(categories: Category[]): Observable<TreeNode[]> {
        return of(
            categories?.map((category) => {
                const children: TreeNode[] = [];
                this.categoryListToTreeNode(category.children).subscribe((treeNodes) => {
                    children.push(...treeNodes);
                });
                return this.categoryToTreeNode(category, children);
            })
        );
    }

    private categoryToTreeNode(category: Category, children: TreeNode[]): TreeNode {
        const node = {
            label: category.name.getLocalisedLabel(this.mainLanguageCode),
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            children,
            data: category,
            selectable: !this.disabled,
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

    private setLoadingNode(node: TreeNode) {
        node.icon = 'fa fa-spinner fa-spin';
        node.selectable = false;
        if (node.children) {
            for (const child of node.children) {
                this.setLoadingNode(child);
            }
        }
    }

    private unsetLoadingNode(node: TreeNode) {
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
