import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE, PAGINATION_OPTIONS } from '@app/app.constants';
import { FilterFilter } from '@app/modules/filter/filter-search/filter-search';
import { Filter } from '@app/shared/model/filter.model';
import { FilterService } from '@app/shared/service/filter/filter.service';
import { ResponseWrapper } from 'arte-ng/model';
import { LazyLoadEvent } from 'primeng/api';

@Component({
    selector: 'app-filter-list',
    templateUrl: './filter-list.component.html',
})
export class FilterListComponent implements OnInit {
    public filters: Filter[];
    public totalItems: number;
    public itemsPerPage: number;
    public columns: any = [
        {
            fieldName: 'name',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'filter.entity.name',
            },
        },
        {
            fieldName: 'login',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'filter.entity.login',
            },
        },
        {
            fieldName: 'lastAccessDate',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'filter.entity.lastAccessDate',
            },
        },
    ];
    private page: any;
    private reverse: boolean;
    private predicate: any;

    constructor(private filterService: FilterService, private activatedRoute: ActivatedRoute, private router: Router, private filterSearch: FilterFilter) {
        this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
            this.itemsPerPage = data['pagingParams'].itemsPerPage;
        });
    }

    public ngOnInit(): void {
        this.activatedRoute.queryParams.subscribe((params) => {
            this.filterSearch.fromQueryParams(params).subscribe(() =>
                this.filterService
                    .find({
                        page: this.page - 1,
                        size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
                        sort: this.sort(),
                        query: this.filterSearch.toQuery(),
                    })
                    .subscribe((rw) => this.onSuccess(rw))
            );
        });
    }

    public transition() {
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

    public loadData(e: LazyLoadEvent) {
        this.page = e.first / e.rows + 1;
        this.itemsPerPage = e.rows;
        if (e.sortField != null) {
            this.predicate = this.filterSearch.fromCamelCaseToSnakeCase(e.sortField);
            this.reverse = e.sortOrder === 1;
        }
        this.transition();
    }

    public sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    public clear() {
        this.page = 0;
        this.router.navigate([
            '/filter',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc'),
            },
        ]);
    }

    public onSuccess(rw: ResponseWrapper) {
        this.totalItems = parseInt(rw.headers.get('X-Total-Count'), 10);
        this.filters = rw.json;
    }
}
