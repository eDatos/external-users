import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ExternalUser, Language, Role, Treatment } from '@app/core/model';
import { PermissionService } from '@app/core/service/auth';
import { ExternalUserService } from '@app/core/service/user';
import { Category, ExternalOperation, Favorite } from '@app/shared/model';
import { FavoriteService } from '@app/shared/service';
import { ArteEventManager, GenericModalService } from 'arte-ng/services';
import { Subscription } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { ExternalUserDeleteDialogComponent } from '../external-user-delete-dialog.component';
import { StructuralResourcesTreeComponent } from '@app/shared/components/structural-resources-tree';

@Component({
    selector: 'app-external-user-form',
    templateUrl: './external-user-form.component.html',
    styleUrls: ['./external-user-form.component.scss'],
})
export class ExternalUserFormComponent implements OnInit, OnDestroy {
    public externalUser: ExternalUser;
    public isSaving: boolean;
    public userId: number;
    public eventSubscriber: Subscription;

    public rolesEnum = Role;
    public languageEnum = Language;
    public treatmentEnum = Treatment;
    public favorites: Favorite[];

    public inEditMode = false;

    private subscription: Subscription;

    @ViewChild(StructuralResourcesTreeComponent)
    public tree: StructuralResourcesTreeComponent;

    constructor(
        private externalUserService: ExternalUserService,
        public permissionService: PermissionService,
        private genericModalService: GenericModalService,
        private favoriteService: FavoriteService,
        private eventManager: ArteEventManager,
        private route: ActivatedRoute,
        private router: Router
    ) {
        const lastUrlSegment = this.route.snapshot.url[this.route.snapshot.url.length - 1].path;
        this.inEditMode = this.inEditMode || lastUrlSegment === 'new';
    }

    public ngOnInit() {
        this.isSaving = false;

        this.subscription = this.route.params.subscribe((params) => {
            this.userId = params['id'];
            this.load(this.userId);
        });

        this.eventSubscriber = this.eventManager.subscribe(ExternalUserDeleteDialogComponent.EVENT_NAME, (response) => {
            this.externalUser = Object.assign(new ExternalUser(), response.content);
        });
    }

    public saveFavorite(resource: Category | ExternalOperation): void {
        const favorite = new Favorite();
        favorite.externalUser = this.externalUser;
        favorite.resource = resource;
        this.favoriteService
            .save(favorite)
            .pipe(finalize(() => this.updateFavorites()))
            .subscribe();
    }

    public load(id: number) {
        if (id) {
            if (!this.inEditMode) {
                this.updateFavorites();
            }
            this.externalUserService.get(id).subscribe((user) => {
                this.externalUser = user;
            });
        } else {
            this.externalUser = new ExternalUser();
        }
    }

    public cancel() {
        if (this.userId) {
            this.load(this.userId);
        } else {
            this.router.navigate(['..']);
        }
        this.toggleEditMode();
    }

    public save() {
        this.isSaving = true;
        if (this.userId) {
            this.externalUserService.update(this.externalUser).subscribe(
                (response) => this.onSaveSuccess(response),
                () => this.onSaveError()
            );
        } else {
            this.externalUserService.create(this.externalUser).subscribe(
                (response) => this.onSaveSuccess(response),
                () => this.onSaveError()
            );
        }
    }

    public delete() {
        this.genericModalService.open(ExternalUserDeleteDialogComponent as Component, { user: this.externalUser }, { container: '.app' });
    }

    public restore() {
        this.genericModalService.open(ExternalUserDeleteDialogComponent as Component, { user: this.externalUser }, { container: '.app' });
    }

    public ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    public deleteFavorite(resource: Category | ExternalOperation): void {
        const favorite = this.favorites.find((fav) => fav.resource.id === resource.id && fav.resource.favoriteType === resource.favoriteType)!;
        this.favoriteService
            .delete(favorite.id)
            .pipe(finalize(() => this.updateFavorites()))
            .subscribe();
    }

    public toggleEditMode() {
        this.inEditMode = !this.inEditMode;
    }

    public edit() {
        this.toggleEditMode();
    }

    private updateFavorites() {
        this.favoriteService.findByUserId(this.userId).subscribe((favorites) => {
            this.favorites = favorites;
        });
    }

    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'userListModification', content: 'OK' });
        this.externalUser = result;
        this.isSaving = false;
        this.toggleEditMode();
        this.router.navigate(['/external-users', this.externalUser.id]);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
