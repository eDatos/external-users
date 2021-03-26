import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { ExternalUserService } from '@app/core/service';
import { Favorite, FavoriteService } from '@app/shared';
import { TranslateService } from '@ngx-translate/core';
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
    public mainLanguageCode: string;
    public externalUserEmailsList: string[];

    constructor(
        private favoriteService: FavoriteService,
        private activatedRoute: ActivatedRoute,
        private titleService: Title,
        private router: Router,
        private translateService: TranslateService,
        private externalUserService: ExternalUserService
    ) {
        this.favorite = this.activatedRoute.snapshot.data['favorite'] ?? new Favorite();
        this.activatedRoute.url.subscribe((segments) => {
            const lastUrlSegment = segments[segments.length - 1].path;
            this.inEditMode = this.inEditMode || lastUrlSegment === 'new';
        });
        this.mainLanguageCode = this.translateService.getDefaultLang();
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

    public updateExternalUserSuggestions(event) {
        this.externalUserService.find({ query: `NAME ILIKE '%${event.query}%' OR EMAIL ILIKE '%${event.query}%'` }).subscribe((results) => {
            this.externalUserEmailsList = results.body.map((user) => user.email);
        });
    }

    public externalUserTemplate(t): string {
        if (t instanceof Object && '_ITEM_TEMPLATE_FIELD_' in t) {
            return t['_ITEM_TEMPLATE_FIELD_'];
        }
        return t;
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
