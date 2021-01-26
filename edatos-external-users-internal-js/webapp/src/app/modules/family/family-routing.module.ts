import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FamilyFormComponent } from './family-form.component';
import { FamilyComponent } from './family.component';
import { FamilyResolve } from './family.resolve';
import { FAMILY_ROLES } from '@app/core/service/auth';
import { UserRouteAccessGuard } from '@app/core/guard/user-route-access.guard';
import { PagingParamsResolver } from 'arte-ng/src/lib/services';
import { ITEMS_PER_PAGE } from '@app/app.constants';

export const familyRoutes: Routes = [
    {
        path: '',
        component: FamilyComponent,
        resolve: {
            pagingParams: PagingParamsResolver
        },
        data: {
            roles: FAMILY_ROLES,
            pageTitle: 'family.home.title',
            defaultPagingParams: {
                page: '1',
                sort: 'code,asc',
                size: ITEMS_PER_PAGE
            }
        },
        canActivate: [UserRouteAccessGuard]
    },
    {
        path: 'new',
        component: FamilyFormComponent,
        data: {
            roles: FAMILY_ROLES,
            pageTitle: 'family.home.title'
        },
        canActivate: [UserRouteAccessGuard]
    },
    {
        path: ':id',
        component: FamilyFormComponent,
        resolve: {
            family: FamilyResolve
        },
        data: {
            roles: FAMILY_ROLES,
            pageTitle: 'family.home.title'
        },
        canActivate: [UserRouteAccessGuard]
    },
    {
        path: ':id/edit',
        component: FamilyFormComponent,
        resolve: {
            family: FamilyResolve
        },
        data: {
            roles: FAMILY_ROLES,
            pageTitle: 'family.home.title'
        },
        canActivate: [UserRouteAccessGuard]
    }
];

@NgModule({
    imports: [RouterModule.forChild(familyRoutes)],
    exports: [RouterModule]
})
export class FamilyRoutingModule {}
