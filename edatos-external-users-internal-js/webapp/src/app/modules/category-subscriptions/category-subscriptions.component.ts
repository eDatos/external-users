import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Mode, StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree';

@Component({
    selector: 'app-category-subscriptions',
    templateUrl: './category-subscriptions.component.html',
    styleUrls: ['./category-subscriptions.component.scss'],
})
export class CategorySubscriptionsComponent implements OnInit {
    public treeMode: Mode = 'view';

    @ViewChild(StructuralResourcesTreeComponent)
    public tree: StructuralResourcesTreeComponent;

    constructor(private activatedRoute: ActivatedRoute) {
        const lastUrlSegment = this.activatedRoute.snapshot.url[this.activatedRoute.snapshot.url.length - 1]?.path;
        this.treeMode = lastUrlSegment === 'edit' ? 'edit' : 'view';
    }

    public ngOnInit(): void {}

    public save(): void {
        this.tree.save();
    }
}
