import { Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output } from '@angular/core';
import { Category, Favorite, Operation, StructuralResourcesTree } from '@app/shared/model';
import { CategoryService } from '@app/shared/service/category/category.service';
import { TranslateService } from '@ngx-translate/core';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';

type Mode = 'view' | 'select';

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
     * If set to 'view', the tree shows the structural resources without any checkbox and
     * they are not selectable. {@link StructuralResourcesTreeComponent#favorites} is not
     * required.
     *
     * If set to 'select', the tree show checkboxes next to each element.
     * {@link StructuralResourcesTreeComponent#favorites} becomes necessary since it's where
     * the selected favorites are synchronized. This is the default mode.
     */
    @Input()
    public mode: Mode = 'select';

    @Output()
    public onResourceSelect = new EventEmitter<Category | Operation>();

    @Output()
    public onResourceUnselect = new EventEmitter<Category | Operation>();

    public resources: TreeNode[] = [];
    public selectedResources: TreeNode[] = [];
    public selectionMode: 'checkbox' | 'single' | 'multiple' = 'checkbox';

    private mainLanguageCode: string;
    private tree: StructuralResourcesTree[] = [];
    private iterableDiffer: IterableDiffer<Favorite>;
    private nodeList: TreeNode[] = [];

    constructor(private categoryService: CategoryService, private translateService: TranslateService, private iterableDiffers: IterableDiffers) {
        this.mainLanguageCode = this.translateService.getDefaultLang();
        this.iterableDiffer = iterableDiffers.find([]).create();
    }

    public ngDoCheck(): void {
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

    private sort(categories: StructuralResourcesTree[]): StructuralResourcesTree[] {
        categories.sort((a, b) => (a.getLocalisedName() < b.getLocalisedName() ? -1 : a.getLocalisedName() === b.getLocalisedName() ? 0 : 1));
        for (const element of categories) {
            this.sort(element.children);
        }
        return categories;
    }

    public createTree() {
        this.categoryListToTreeNode(this.tree).subscribe((treeNodes) => {
            console.log('treeNodes', treeNodes);
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

    private categoryListToTreeNode(categories: StructuralResourcesTree[]): Observable<TreeNode[]> {
        return of(
            categories?.map((category) => {
                const children: TreeNode[] = [];

                this.categoryListToTreeNode(category.children).subscribe((treeNodes) => {
                    children.push(...treeNodes);
                });

                return category.type === 'category' ? this.categoryToTreeNode(category, children) : this.operationToTreeNode(category);
            })
        );
    }

    private categoryToTreeNode(category: StructuralResourcesTree, children: TreeNode[]): TreeNode {
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

    private operationToTreeNode(operation: StructuralResourcesTree): TreeNode {
        const node = {
            label: operation.name.getLocalisedLabel(this.mainLanguageCode),
            collapsedIcon: 'fa fa-table',
            expandedIcon: 'fa fa-table',
            expanded: true,
            data: operation,
            leaf: true,
            selectable: !this.disabled,
        };

        if (this.isFavorite(operation)) {
            this.selectedResources.push(node);
        }
        this.nodeList.push(node);

        return node;
    }

    private isFavorite(resource: StructuralResourcesTree): boolean {
        return this.favorites?.some((favorite) => favorite.resource?.id === resource.id && favorite.resource.type === resource.type) ?? false;
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
        }
    }
}
