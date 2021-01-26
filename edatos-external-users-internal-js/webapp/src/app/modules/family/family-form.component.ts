import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { Family, FamilyService, InternationalString, ProcStatusEnum } from '@app/shared';
import { FamilyDeleteDialogComponent } from './family-delete-dialog.component';
import { HasTitlesContainer } from 'arte-ng';
import { ArteEventManager, GenericModalService } from 'arte-ng/src/lib/services';
import { TextValidator } from 'arte-ng/src/lib/validators';
import { ConfigService } from '@app/config';
@Component({
    selector: 'app-family-form',
    templateUrl: './family-form.component.html'
})
export class FamilyFormComponent implements OnInit, OnDestroy, HasTitlesContainer {
    public static EVENT_NAME = 'familyListModification';

    public instance: FamilyFormComponent;
    public family: Family;
    public isSaving: boolean;
    public editForm: FormGroup;

    @ViewChild('titlesContainer')
    private titlesContaner: ElementRef;
    private updatesSubscription: Subscription;

    constructor(
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private familyService: FamilyService,
        private genericModalService: GenericModalService,
        private eventManager: ArteEventManager,
        private configService: ConfigService
    ) {
        this.instance = this;
    }

    ngOnInit() {
        this.isSaving = false;
        if (this.activatedRoute.snapshot.data['family']) {
            this.family = this.activatedRoute.snapshot.data['family'];
        } else {
            this.family = new Family();
        }
        this.initializeFormGroup();
        this.registerChangesOnFamily();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.updatesSubscription);
    }

    delete(): void {
        this.genericModalService.open(<any>FamilyDeleteDialogComponent, { family: this.family }).result.subscribe((res) => {
            this.router.navigate(['/family']);
        });
    }

    isEditMode(): boolean {
        const lastPath = this.activatedRoute.snapshot.url[this.activatedRoute.snapshot.url.length - 1].path;
        return lastPath === 'edit' || lastPath === 'new';
    }

    clear(): void {
        if (this.family.id) {
            this.router.navigate(['/family', this.family.id]);
        } else {
            this.router.navigate(['/family']);
        }
    }

    save(): void {
        this.isSaving = true;
        if (this.family.id) {
            this.subscribeToSaveResponse(this.familyService.update(this.family));
        } else {
            this.subscribeToSaveResponse(this.familyService.create(this.family));
        }
    }

    publishInternally(): void {
        this.familyService.publishInternally(this.family.id).subscribe(
            (res: Family) => this.load(res));
    }

    publishExternally(): void {
        this.familyService.publishExternally(this.family.id).subscribe(
            (res: Family) => this.load(res));
    }

    getTitlesContainer(): ElementRef {
        return this.titlesContaner;
    }

    existsFamily(): boolean {
        return !!this.family && !!this.family.id;
    }

    isValidForm(): boolean {
        return this.editForm.valid && !this.isSaving;
    }

    getText(internationalString: InternationalString): string {
        let language = this.configService.getConfig().metadata.defaultLanguage;
        let localisedString = internationalString?.texts.find(l => l.locale === language);
        return localisedString?.label;
    }

    canPublishInternally(): boolean {
        return this.family.procStatus === ProcStatusEnum.DRAFT;
    }

    canPublishExternally(): boolean {
        return this.family.procStatus === ProcStatusEnum.INTERNALLY_PUBLISHED;
    }

    private initializeFormGroup(): void {
        this.editForm = new FormGroup({
            code: new FormControl('', Validators.compose([TextValidator.required, TextValidator.maxLenghtShortText])),
            title: new FormControl('', [TextValidator.required, TextValidator.maxLenghtShortText])
        });
    }

    private registerChangesOnFamily(): void {
        this.updatesSubscription = this.eventManager.subscribe(FamilyFormComponent.EVENT_NAME, (result) => {
            if (result.content !== 'saved') {
                this.load(result.content);
            }
        });
    }

    private load(res: Family): void {
        this.family = Object.assign({}, res);
    }

    private subscribeToSaveResponse(result: Observable<Family>): void {
        result.subscribe(
            (res: Family) => this.onSaveSuccess(res),
            (err) => this.onSaveError(err)
        );
    }

    private onSaveSuccess(result: Family): void {
        this.isSaving = false;
        this.eventManager.broadcast({ name: FamilyFormComponent.EVENT_NAME, content: 'saved' });
        this.router.navigate(['family', result.id]);
    }

    private onSaveError(err: any): void {
        this.isSaving = false;
    }
}
