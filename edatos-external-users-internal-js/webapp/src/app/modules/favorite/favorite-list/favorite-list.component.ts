import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE, PAGINATION_OPTIONS } from '@app/app.constants';
import { FavoriteFilter } from '@app/modules/favorite/favorite-search/favorite-search';
import { InternationalString } from '@app/shared';
import { Favorite } from '@app/shared/model/favorite.model';
import { FavoriteService } from '@app/shared/service/favorite/favorite.service';
import { TranslateService } from '@ngx-translate/core';
import { ResponseWrapper } from 'arte-ng/model';
import { LazyLoadEvent } from 'primeng/api';

@Component({
    selector: 'app-favorite-list',
    templateUrl: './favorite-list.component.html',
})
export class FavoriteListComponent implements OnInit {
    public favorites: Favorite[];
    public totalItems: number;
    public itemsPerPage: number;
    public columns: any = [
        {
            fieldName: 'email',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'favorite.entity.email',
            },
        },
        {
            fieldName: 'subjectArea',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'category.name',
            },
        },
    ];
    private mainLanguageCode: string;
    private page: any;
    private reverse: boolean;
    private predicate: any;

    constructor(
        private favoriteService: FavoriteService,
        private favoriteSearch: FavoriteFilter,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private translateService: TranslateService
    ) {
        this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
            this.itemsPerPage = data['pagingParams'].itemsPerPage;
        });
        this.mainLanguageCode = this.translateService.getDefaultLang();
    }

    public ngOnInit(): void {
        this.activatedRoute.queryParams.subscribe((params) => {
            this.favoriteSearch.fromQueryParams(params).subscribe(() =>
                this.favoriteService
                    .find({
                        page: this.page - 1,
                        size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
                        sort: this.sort(),
                        query: this.favoriteSearch.toQuery(),
                    })
                    .subscribe((rw) => this.onSuccess(rw))
            );
        });
    }

    public transition(): void {
        const transitionParams = {
            page: this.page,
            size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc'),
        };
        const queryParams = {
            ...this.favoriteSearch.toUrl(this.activatedRoute.snapshot.queryParams),
            ...transitionParams,
        };
        this.router.navigate(['/favorite'], { replaceUrl: true, queryParams });
    }

    public loadData(e: LazyLoadEvent): void {
        this.page = e.first / e.rows + 1;
        this.itemsPerPage = e.rows;
        if (e.sortField != null) {
            this.predicate = this.favoriteSearch.fromCamelCaseToSnakeCase(e.sortField);
            this.reverse = e.sortOrder === 1;
        }
        this.transition();
    }

    public sort(): string[] {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    public clear(): void {
        this.page = 0;
        this.router.navigate([
            '/favorite',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc'),
            },
        ]);
    }

    public getLocalisedString(internationalString: InternationalString): string | undefined {
        return internationalString.texts.find((localisedString) => localisedString.locale === this.mainLanguageCode).label;
    }

    private onSuccess(response: ResponseWrapper) {
        this.totalItems = parseInt(response.headers.get('X-Total-Count'), 10);
        this.favorites = response.json;
    }
}
