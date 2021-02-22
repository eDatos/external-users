import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE, PAGINATION_OPTIONS } from '@app/app.constants';
import { FilterFilter } from '@app/modules/filter/filter-search/filter-search';
import { Filter } from '@app/shared/model/filter.model';
import { FilterService } from '@app/shared/service/filter/filter.service';
import { TranslateService } from '@ngx-translate/core';
import { ResponseWrapper } from 'arte-ng/model';
import { LazyLoadEvent } from 'primeng/api';

@Component({
    selector: 'app-filter',
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.scss'],
})
export class FilterComponent implements OnInit {
    filters: Filter[];
    totalItems: number;
    itemsPerPage: number;
    columns: any = [
        {
            fieldName: 'name',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'filter.name.label',
            },
        },
        {
            fieldName: 'login',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'filter.login.label',
            },
        },
        {
            fieldName: 'lastAccessDate',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'filter.lastAccessDate.label',
            },
        },
    ];
    private page: any;
    private reverse: boolean;
    private predicate: any;

    constructor(
        private filterService: FilterService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private filterSearch: FilterFilter,
        private titleService: Title,
        private translateService: TranslateService
    ) {
        this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
            this.itemsPerPage = data['pagingParams'].itemsPerPage;
        });
        this.translateService
            .get(['app.name.short', 'filter.home.title'])
            .subscribe(({ 'app.name.short': appName, 'filter.home.title': filterHomeTitle }) => {
                this.titleService.setTitle(`${appName} - ${filterHomeTitle}`);
            });
    }

    ngOnInit(): void {
        this.activatedRoute.queryParams.subscribe((params) => {
            this.filterSearch.fromQueryParams(params).subscribe(() =>
                this.filterService
                    .find({
                        page: this.page - 1,
                        size:
                            PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1
                                ? this.itemsPerPage
                                : ITEMS_PER_PAGE,
                        sort: this.sort(),
                        query: this.filterSearch.toQuery(),
                    })
                    .subscribe((rw) => this.onSuccess(rw))
            );
        });
    }

    transition() {
        const transitionParams = {
            page: this.page,
            size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc'),
        };
        const queryParams = {
            ...this.filterSearch.toUrl(this.activatedRoute.snapshot.queryParams),
            ...transitionParams,
        };
        this.router.navigate(['/filter'], { replaceUrl: true, queryParams });
    }

    loadData(e: LazyLoadEvent) {
        this.page = e.first / e.rows + 1;
        this.itemsPerPage = e.rows;
        if (e.sortField != null) {
            // TODO(EDATOS-3280): Why would this be necessary? Maybe instead the criteria processor on the server
            //  should use LASTACCESSDATE instead of LAST_ACCESS_DATE?
            this.predicate = this.filterSearch.fromCamelCaseToSnakeCase(e.sortField);
            this.reverse = e.sortOrder === 1;
        }
        this.transition();
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/filter',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc'),
            },
        ]);
    }

    onSuccess(rw: ResponseWrapper) {
        this.totalItems = parseInt(rw.headers.get('X-Total-Count'), 10);
        this.filters = rw.json;
    }
}
