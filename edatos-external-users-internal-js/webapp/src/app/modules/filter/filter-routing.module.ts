import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ITEMS_PER_PAGE } from '@app/app.constants';
import { UserRouteAccessGuard } from '@app/core/guard';
import { FILTER_ROLES } from '@app/core/service';
import { FilterFormComponent } from '@app/modules/filter/filter-form/filter-form.component';
import { FilterListComponent } from '@app/modules/filter/filter-list/filter-list.component';
import { FilterResolver } from '@app/modules/filter/filter.resolver';
import { PagingParamsResolver } from 'arte-ng/services';

export const FILTER_ROUTES: Routes = [
    {
        path: '',
        component: FilterListComponent,
        resolve: {
            pagingParams: PagingParamsResolver,
        },
        data: {
            roles: FILTER_ROLES,
            pageTitle: 'filter.home.title',
            defaultPagingParams: {
                page: '1',
                sort: 'created_date,desc',
                size: ITEMS_PER_PAGE,
            },
        },
        canActivate: [UserRouteAccessGuard],
    },
    {
        path: ':id',
        component: FilterFormComponent,
        resolve: {
            filter: FilterResolver,
        },
        data: {
            roles: FILTER_ROLES,
            pageTitle: 'filter.name',
        },
        canActivate: [UserRouteAccessGuard],
    },
];

@NgModule({
    imports: [RouterModule.forChild(FILTER_ROUTES)],
    exports: [RouterModule],
})
export class FilterRoutingModule {}
