import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Family, FamilyService, InternationalString } from '@app/shared';
import { FamilyFilter, FamilySearchComponent } from './family-search';
import { FamilyFormComponent } from './family-form.component';
import { ITEMS_PER_PAGE, PAGINATION_OPTIONS } from '@app/app.constants';
import { LazyLoadEvent } from 'primeng/api';
import { ResponseWrapper } from 'arte-ng/src/lib/model';
import { ArteEventManager } from 'arte-ng/src/lib/services';
import { ConfigService } from '@app/config';

@Component({
    selector: 'app-family',
    templateUrl: './family.component.html'
})
export class FamilyComponent implements OnInit, OnDestroy {
    private eventSubscriber: Subscription;
    private searchSubscription: Subscription;

    public totalItems: any;
    public itemsPerPage: any;
    public page: any;
    public predicate: any;
    public reverse: any;
    public filters: FamilyFilter;
    public families: Family[];

    public columns: any = [
        {
            fieldName: 'code',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'family.code.label'
            }
        },
        {
            fieldName: 'title',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'family.title.label'
            }
        }
    ]

    constructor(
        private datePipe: DatePipe,
        private familyService: FamilyService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: ArteEventManager,
        private configService: ConfigService
    ) {
        this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
            this.itemsPerPage = data['pagingParams'].itemsPerPage;
        });
    }

    ngOnInit() {
        this.filters = new FamilyFilter(this.datePipe);
        this.processUrlParams();
        this.activatedRoute.queryParams.subscribe((params) => {
            this.filters.fromQueryParams(params).subscribe(() => this.buscarTodas());
        });
        this.registerChangeInFamilies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.eventManager.destroy(this.searchSubscription);
    }

    transition() {
        const transitionParams = {
            page: this.page,
            size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        };
        const queryParams = { ...this.filters.toUrl(this.activatedRoute.snapshot.queryParams), ...transitionParams };
        this.router.navigate(['/family'], { queryParams });
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/family',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
    }

    loadData(e: LazyLoadEvent) {
        this.page = (e.first / e.rows) + 1;
        this.itemsPerPage = e.rows;
        if (e.sortField != null) {
            this.predicate = e.sortField;
            this.reverse = e.sortOrder == 1;
        }
        this.transition();
    }

    getText(internationalString: InternationalString): string {
        let language = this.configService.getConfig().metadata.defaultLanguage;
        let localisedString = internationalString?.texts.find(l => l.locale === language);
        return localisedString?.label;
    }

    private buscarTodas() {
        this.familyService
            .find({
                page: this.page - 1,
                size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
                sort: this.sort(),
                query: this.filters.toQuery()
            })
            .subscribe((res: ResponseWrapper) => this.onSuccess(res.json, res.headers));
    }

    private registerChangeInFamilies() {
        this.eventSubscriber = this.eventManager.subscribe(FamilyFormComponent.EVENT_NAME, (response) => this.buscarTodas());
        this.searchSubscription = this.eventManager.subscribe(FamilySearchComponent.EVENT_NAME, (response) => {
            this.page = null;
            const queryParams = Object.assign({}, this.filters.toUrl(this.activatedRoute.snapshot.queryParams));
            this.router.navigate(['family'], { queryParams });
        });
    }

    private sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.totalItems = headers.get('X-Total-Count');
        this.families = data;
    }

    private processUrlParams(): void {
        this.filters.code = this.activatedRoute.snapshot.queryParams.code;
        this.filters.title = this.activatedRoute.snapshot.queryParams.title;
    }
}
