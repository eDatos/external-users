import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ExternalUser, Language, Role, Treatment } from '@app/core/model';
import { PermissionService } from '@app/core/service/auth';
import { ExternalUserService } from '@app/core/service/user';
import { Category, Favorite, Operation } from '@app/shared/model';
import { FavoriteService } from '@app/shared/service';
import { ArteEventManager, GenericModalService } from 'arte-ng/services';
import { Subscription } from 'rxjs';
import { ExternalUserDeleteDialogComponent } from '../external-user-delete-dialog.component';

@Component({
    selector: 'app-external-user-form',
    templateUrl: './external-user-form.component.html',
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

    private subscription: Subscription;

    constructor(
        private externalUserService: ExternalUserService,
        public permissionService: PermissionService,
        private genericModalService: GenericModalService,
        private favoriteService: FavoriteService,
        private eventManager: ArteEventManager,
        private route: ActivatedRoute,
        private router: Router
    ) {}

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

    public saveFavorite(resource: Category | Operation): void {
        const favorite = new Favorite();
        favorite.externalUser = this.externalUser;
        favorite.resource = resource;
        this.favoriteService.save(favorite).subscribe(
            () => {
                this.updateFavorites();
            },
            (error) => {
                this.updateFavorites();
            }
        );
    }

    public isEditMode(): boolean {
        const lastPath = this.route.snapshot.url[this.route.snapshot.url.length - 1].path;
        return lastPath === 'edit' || lastPath === 'new';
    }

    public load(id: number) {
        if (id) {
            if (!this.isEditMode()) {
                this.updateFavorites();
            }
            this.externalUserService.get(id).subscribe((user) => {
                this.externalUser = user;
            });
        } else {
            this.externalUser = new ExternalUser();
        }
    }

    public clear() {
        const returnPath: (string | number)[] = ['/external-users'];
        if (this.userId) {
            returnPath.push(this.userId);
        }
        this.router.navigate(returnPath);
    }

    public save() {
        this.isSaving = true;
        if (this.externalUserExists()) {
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

    public externalUserExists(): boolean {
        return !!this.externalUser?.id;
    }

    public ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    public deleteFavorite(resource: Category | Operation): void {
        const favorite = this.favorites.find((fav) => fav.resource.id === resource.id && fav.resource.constructor === resource.constructor);
        this.favoriteService.delete(favorite.id).subscribe(
            () => {
                this.updateFavorites();
            },
            (error) => {
                this.updateFavorites();
            }
        );
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
        this.router.navigate(['/external-users', this.externalUser.id]);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
