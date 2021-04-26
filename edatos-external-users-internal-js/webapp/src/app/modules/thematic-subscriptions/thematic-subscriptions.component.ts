import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Mode } from '@app/shared/components/structural-resources-tree';

@Component({
    selector: 'app-thematic-subscriptions',
    templateUrl: './thematic-subscriptions.component.html',
    styleUrls: ['./thematic-subscriptions.component.scss'],
})
export class ThematicSubscriptionsComponent implements OnInit {
    public treeMode: Mode = 'view';

    constructor(private activatedRoute: ActivatedRoute) {
        const lastUrlSegment = this.activatedRoute.snapshot.url[this.activatedRoute.snapshot.url.length - 1]?.path;
        this.treeMode = lastUrlSegment === 'edit' ? 'edit' : 'view';
    }

    public ngOnInit(): void {}
}
