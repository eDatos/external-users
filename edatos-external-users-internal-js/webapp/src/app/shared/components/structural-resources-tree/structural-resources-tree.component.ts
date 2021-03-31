import { Component, Input, OnInit } from '@angular/core';
import { Category, Favorite, Operation } from '@app/shared/model';
import { CategoryService } from '@app/shared/service/category/category.service';
import { OperationService } from '@app/shared/service/operation/operation.service';
import { ArteAlertService } from 'arte-ng/services';
import { TreeNode } from 'primeng/api';

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
            this.resources = this.createTree(categories);
        });
    }

    public nodeSelect(event) {
        this.alertService.error({ severity: 'info', summary: 'Node Selected', detail: event.node.label });
    }

    public nodeUnselect(event) {
        this.alertService.error({ severity: 'info', summary: 'Node Unselected', detail: event.node.label });
    }

    private createTree(categories: Category[]): TreeNode[] {
        return categories?.map((category) => {
            const children = [...this.createTree(category.children)];
            this.operationService.find({ query: `CATEGORY_ID EQ ${category.id}` }).subscribe((operations) => {
                children.push(...operations.map((operation) => this.toTreeNode(operation)));
            });
            return {
                label: category.name.getLocalisedLabel('es'),
                collapsedIcon: 'fa fa-folder',
                expandedIcon: 'fa fa-folder-open',
                expanded: true,
                children,
                leaf: false,
            };
        });
    }

    private toTreeNode(operation: Operation): TreeNode {
        return {
            label: operation.name.getLocalisedLabel('es'),
            icon: 'fa fa-table',
            expanded: true,
            leaf: true,
        };
    }
}
