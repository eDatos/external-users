import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '@app/core/model';
import { Category, Favorite, Operation } from '@app/shared/model';
import { FavoriteService } from '@app/shared/service';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-favorite-tree',
    templateUrl: './favorite-tree.component.html',
})
export class FavoriteTreeComponent implements OnInit {
    public inEditMode = false;
    public isSaving = false;
    public isLoading = false;
    public favorite: Favorite;
    public mainLanguageCode: string;

    public user: User;
    public eventSubscriber: Subscription;
    public favorites: Favorite[];

    constructor(private favoriteService: FavoriteService, private translateService: TranslateService, private route: ActivatedRoute) {
        this.mainLanguageCode = this.translateService.getDefaultLang();
    }

    public ngOnInit() {
        this.isSaving = false;
        this.user = this.route.snapshot.data.user;
        this.favorites = this.route.snapshot.data.favorites;
    }

    public saveFavorite(resource: Category | Operation): void {
        const favorite = new Favorite();
        favorite.externalUser = this.user;
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
        this.favoriteService.findByUserId().subscribe((favorites) => {
            this.favorites = favorites;
        });
    }
}
