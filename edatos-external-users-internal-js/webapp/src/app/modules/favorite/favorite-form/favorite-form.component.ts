import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Favorite, FavoriteService } from '@app/shared';
import { finalize } from 'rxjs/operators';

@Component({
    selector: 'app-favorite-form',
    templateUrl: './favorite-form.component.html',
})
export class FavoriteFormComponent implements OnInit {
    public inEditMode = false;
    public isSaving = false;
    public isLoading = false;
    public favorite: Favorite;

    constructor(private favoriteService: FavoriteService, private activatedRoute: ActivatedRoute, private titleService: Title, private router: Router) {
        this.favorite = this.activatedRoute.snapshot.data['favorite'] ?? new Favorite();
        this.activatedRoute.url.subscribe((segments) => {
            const lastUrlSegment = segments[segments.length - 1].path;
            this.inEditMode = this.inEditMode || lastUrlSegment === 'new';
        });
    }

    public ngOnInit() {
        if (!this.inEditMode) {
            this.titleService.setTitle(this.titleService.getTitle() + ' ' + this.favorite.id);
        }
    }

    public toggleEditMode(): void {
        this.inEditMode = !this.inEditMode;
    }

    public cancel(): void {
        this.updateData();
    }

    public edit(): void {
        this.toggleEditMode();
    }

    public submit(): void {
        this.toggleIsSaving();
        if (this.favorite.id == null) {
            this.favoriteService
                .save(this.favorite)
                .pipe(finalize(() => this.toggleIsSaving()))
                .subscribe((favorite) => {
                    this.router.navigate([`../${favorite.id}`], { relativeTo: this.activatedRoute });
                });
        } else {
            this.favoriteService
                .update(this.favorite)
                .pipe(finalize(() => this.toggleIsSaving()))
                .subscribe((favorite) => {
                    this.favorite = favorite;
                    this.toggleEditMode();
                });
        }
    }

    private toggleIsSaving(): void {
        this.isSaving = !this.isSaving;
    }

    private updateData(): void {
        this.showSpinner();
        this.favoriteService.get(this.favorite.id).subscribe((favorite) => {
            this.favorite = favorite;
            this.hideSpinner();
            this.toggleEditMode();
        });
    }

    private showSpinner(): void {
        this.isLoading = true;
    }

    private hideSpinner(): void {
        this.isLoading = false;
    }
}
