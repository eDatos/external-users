import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE, PAGINATION_OPTIONS } from '@app/app.constants';
import { ExternalUser, User } from '@app/core/model';
import { PermissionService } from '@app/core/service/auth';
import { ExternalUserService } from '@app/core/service/user';
import { ExternalUserFilter } from '@app/modules/admin/external-user-management/external-user-search/external-user-filter';
import { ResponseWrapper } from 'arte-ng/model';

import { ArteEventManager } from 'arte-ng/services';
import { LazyLoadEvent } from 'primeng/api';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-external-user-list',
    templateUrl: './external-user-list.component.html',
})
export class ExternalUserListComponent implements OnInit, OnDestroy {
    private eventSubscriber: Subscription;
    private searchSubscription: Subscription;

    public totalItems: any;
    public itemsPerPage: any;
    public page: any;
    public predicate: any;
    public reverse: any;
    public filters: ExternalUserFilter;
    public users: ExternalUser[];

    public columns = [
        {
            fieldName: 'name',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'externalUserManagement.name',
            },
        },
        {
            fieldName: 'surname1',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'externalUserManagement.surname1',
            },
        },
        {
            fieldName: 'surname2',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'externalUserManagement.surname2',
            },
        },
        {
            fieldName: 'email',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'externalUserManagement.email',
            },
        },
        {
            fieldName: 'language',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'externalUserManagement.language',
            },
        },
        {
            fieldName: 'treatment',
            header: {
                handler: 'translate',
                translatePath: 'externalUserManagement.treatment',
            },
        },
        {
            fieldName: 'status',
            header: {
                handler: 'translate',
                translatePath: 'externalUserManagement.status.name',
            },
        },
    ];

    constructor(
        public permissionService: PermissionService,
        private userService: ExternalUserService,
        private eventManager: ArteEventManager,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {
        this.filters = new ExternalUserFilter();
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
            this.itemsPerPage = data['pagingParams'].itemsPerPage;
        });
    }

    public ngOnInit() {
        this.processUrlParams();
        this.loadAll();
        this.registerChangeInUsers();
    }

    public ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.eventManager.destroy(this.searchSubscription);
    }

    public registerChangeInUsers() {
        this.eventSubscriber = this.eventManager.subscribe('userListModification', (response) => this.loadAll());
        this.searchSubscription = this.eventManager.subscribe('userSearch', (response) => {
            this.page = 1;
            const queryParams = Object.assign({}, this.filters.toUrl(this.activatedRoute.snapshot.queryParams));
            this.router.navigate(['/admin', 'user-management'], { queryParams });
            this.loadAll();
        });
    }

    public loadAll() {
        this.userService
            .find({
                page: this.page - 1,
                size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
                sort: this.sort(),
                query: this.filters.toQuery(),
            })
            .subscribe((res: ResponseWrapper) => this.onSuccess(res.json, res.headers));
    }

    public sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    public loadData(e: LazyLoadEvent) {
        this.page = e.first / e.rows + 1;
        this.itemsPerPage = e.rows;
        if (e.sortField != null) {
            this.predicate = e.sortField;
            this.reverse = e.sortOrder == 1;
        }
        this.transition();
    }

    public transition() {
        this.router.navigate(['/admin', 'user-management'], {
            queryParams: {
                page: this.page,
                size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc'),
            },
        });
        this.loadAll();
    }

    private onSuccess(data, headers) {
        this.totalItems = headers.get('X-Total-Count');
        this.users = data;
    }

    private processUrlParams(): void {
        this.filters.includeDeleted = this.activatedRoute.snapshot.queryParams.hasOwnProperty('includeDeleted');
        this.filters.name = this.activatedRoute.snapshot.queryParams.name;
    }

    public isActivo(user: User): boolean {
        return !user.deletionDate;
    }
}
