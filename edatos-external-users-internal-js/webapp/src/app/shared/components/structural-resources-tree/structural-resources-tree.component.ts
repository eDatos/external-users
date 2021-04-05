import { Component, Input, OnInit } from '@angular/core';
import { Category, Favorite, Operation } from '@app/shared/model';
import { CategoryService } from '@app/shared/service/category/category.service';
import { OperationService } from '@app/shared/service/operation/operation.service';
import { TranslateService } from '@ngx-translate/core';
import { ArteAlertService } from 'arte-ng/services';
import { TreeNode } from 'primeng/api';
import { Observable, of } from 'rxjs';

@Component({
    selector: 'app-structural-resources-tree',
    templateUrl: './structural-resources-tree.component.html',
})
export class StructuralResourcesTreeComponent implements OnInit {
    @Input()
    public favorites: Favorite[];

    public resources: TreeNode[];
    public selectedResources: TreeNode[] = [];
    private mainLanguageCode: string;

    constructor(
        private alertService: ArteAlertService,
        private categoryService: CategoryService,
        private operationService: OperationService,
        private translateService: TranslateService
    ) {}

    public ngOnInit(): void {
        this.categoryService.getTree().subscribe((categories) => {
            this.categoryListToTreeNode(categories).subscribe((treeNodes) => {
                this.resources = treeNodes;
                this.selectResources();
            });
        });

        this.mainLanguageCode = this.translateService.getDefaultLang();
    }

    private categoryListToTreeNode(categories: Category[]): Observable<TreeNode[]> {
        return of(
            categories?.map((category) => {
                const children = [];

                // add category children
                this.categoryListToTreeNode(category.children).subscribe((treeNodes) => {
                    children.push(...treeNodes);
                });

                // add operations attached to the category
                this.operationService.find({ query: `CATEGORY_ID EQ ${category.id}` }).subscribe((operations) => {
                    children.push(...operations.map((operation) => this.operationToTreeNode(operation)));
                });

                return this.categoryToTreeNode(category, children);
            })
        );
    }

    private categoryToTreeNode(category: Category, children: TreeNode[]): TreeNode {
        return {
            label: category.name.getLocalisedLabel(this.mainLanguageCode),
            collapsedIcon: 'fa fa-folder',
            expandedIcon: 'fa fa-folder-open',
            expanded: true,
            children,
            data: category,
        };
    }

    private operationToTreeNode(operation: Operation): TreeNode {
        return {
            label: operation.name.getLocalisedLabel(this.mainLanguageCode),
            icon: 'fa fa-table',
            expanded: true,
            data: operation,
            leaf: true,
        };
    }

    private selectResources(): void {
        for (const fav of this.favorites) {
            const resource = this.resources.find((r) => r.data.id === fav.getResource().id);
            this.selectedResources.push(resource);
        }
    }
}
