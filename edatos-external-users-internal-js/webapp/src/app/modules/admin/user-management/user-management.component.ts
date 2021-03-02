import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE, PAGINATION_OPTIONS } from '@app/app.constants';
import { User } from '@app/core/model';
import { PermissionService } from '@app/core/service/auth';
import { UserService } from '@app/core/service/user';
import { ResponseWrapper } from 'arte-ng/model';

import { ArteEventManager } from 'arte-ng/services';
import { LazyLoadEvent } from 'primeng/api';
import { Subscription } from 'rxjs';

import { UserFilter } from './user-search';

@Component({
    selector: 'app-user-mgmt',
    templateUrl: './user-management.component.html'
})
export class UserMgmtComponent implements OnInit, OnDestroy {
    private eventSubscriber: Subscription;
    private searchSubscription: Subscription;

    public totalItems: any;
    public itemsPerPage: any;
    public page: any;
    public predicate: any;
    public reverse: any;
    public filters: UserFilter;
    public users: User[];

    public columns = [
        {
            fieldName: 'login',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'userManagement.login'
            }
        },
        {
            fieldName: 'nombre',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'userManagement.nombre'
            }
        },
        {
            fieldName: 'apellido1',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'userManagement.apellido1'
            }
        },
        {
            fieldName: 'apellido2',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'userManagement.apellido2'
            }
        },
        {
            fieldName: 'email',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'userManagement.email'
            }
        },
        {
            fieldName: 'language',
            sortable: true,
            header: {
                handler: 'translate',
                translatePath: 'userManagement.language'
            }
        },
        {
            fieldName: 'roles',
            header: {
                handler: 'translate',
                translatePath: 'userManagement.rol'
            }
        }
    ]

    constructor(
        public permissionService: PermissionService,
        private userService: UserService,
        private eventManager: ArteEventManager,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {
        this.filters = new UserFilter();
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
            this.itemsPerPage = data['pagingParams'].itemsPerPage;
        });
    }

    ngOnInit() {
        this.processUrlParams();
        this.loadAll();
        this.registerChangeInUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.eventManager.destroy(this.searchSubscription);
    }

    registerChangeInUsers() {
        this.eventSubscriber = this.eventManager.subscribe('userListModification', (response) => this.loadAll());
        this.searchSubscription = this.eventManager.subscribe('userSearch', (response) => {
            this.page = 1;
            const queryParams = Object.assign({}, this.filters.toUrl(this.activatedRoute.snapshot.queryParams));
            this.router.navigate(['/admin', 'user-management'], { queryParams });
            this.loadAll();
        });
    }

    loadAll() {
        this.userService
            .find({
                page: this.page - 1,
                size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
                sort: this.sort(),
                query: this.filters.toQuery()
            })
            .subscribe((res: ResponseWrapper) => this.onSuccess(res.json, res.headers));
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
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

    transition() {
        this.router.navigate(['/admin', 'user-management'], {
            queryParams: {
                page: this.page,
                size: PAGINATION_OPTIONS.indexOf(Number(this.itemsPerPage)) > -1 ? this.itemsPerPage : ITEMS_PER_PAGE,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
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
        this.filters.rol = this.activatedRoute.snapshot.queryParams.rol;
    }

    public isActivo(user: User): boolean {
        return !user.deletionDate;
    }
}
