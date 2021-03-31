import { Component, Input, OnInit } from '@angular/core';
import { Category, Favorite, Operation } from '@app/shared/model';
import { CategoryService } from '@app/shared/service/category/category.service';
import { OperationService } from '@app/shared/service/operation/operation.service';
import { ArteAlertService } from 'arte-ng/services';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';

@Component({
    selector: 'app-structural-resources-tree',
    templateUrl: './structural-resources-tree.component.html',
})
export class StructuralResourcesTreeComponent implements OnInit {
    @Input()
    public favorite: Favorite;

    public resources: TreeNode[];
    public selectedResources: TreeNode[];

    constructor(private alertService: ArteAlertService, private categoryService: CategoryService, private operationService: OperationService) {}

    public ngOnInit(): void {
        this.categoryService.getTree().subscribe((categories) => {
            this.categoryListToTreeNode(categories).subscribe((treeNodes) => {
                this.resources = treeNodes;
            });
        });
    }

    public nodeSelect(event) {
        console.log(event);
    }

    public nodeUnselect(event) {
        this.alertService.error({ severity: 'info', summary: 'Node Unselected', detail: event.node.label });
    }

    private categoryListToTreeNode(categories: Category[]): Observable<TreeNode[]> {
        return of(
            categories?.map((category) => {
                const children = [];

                this.categoryListToTreeNode(category.children).subscribe((treeNodes) => {
                    children.push(...treeNodes);
                });

                this.operationService.find({ query: `CATEGORY_ID EQ ${category.id}` }).subscribe((operations) => {
                    children.push(...operations.map((operation) => this.operatioToTreeNode(operation)));
                });

                return this.categoryToTreeNode(category, children);
            })
        );
    }

    private categoryToTreeNode(category: Category, children: TreeNode[]): TreeNode {
        return {
            label: category.name.getLocalisedLabel('es'),
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            children,
            data: category,
            leaf: false,
        };
    }

    private operatioToTreeNode(operation: Operation): TreeNode {
        return {
            label: operation.name.getLocalisedLabel('es'),
            icon: 'fa fa-table',
            expanded: true,
            data: operation,
            leaf: true,
        };
    }
}
