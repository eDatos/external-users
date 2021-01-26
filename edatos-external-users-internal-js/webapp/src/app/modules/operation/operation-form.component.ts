import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { Operation, OperationService, InternationalString, ProcStatusEnum } from '@app/shared';
import { OperationDeleteDialogComponent } from './operation-delete-dialog.component';
import { HasTitlesContainer } from 'arte-ng';
import { ArteEventManager, GenericModalService } from 'arte-ng/src/lib/services';
import { TextValidator } from 'arte-ng/src/lib/validators';
import { ConfigService } from '@app/config';
@Component({
    selector: 'app-operation-form',
    templateUrl: './operation-form.component.html'
})
export class OperationFormComponent implements OnInit, OnDestroy, HasTitlesContainer {
    public static EVENT_NAME = 'operationListModification';

    public instance: OperationFormComponent;
    public operation: Operation;
    public isSaving: boolean;
    public editForm: FormGroup;

    @ViewChild('titlesContainer')
    private titlesContaner: ElementRef;
    private updatesSubscription: Subscription;

    constructor(
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private operationService: OperationService,
        private genericModalService: GenericModalService,
        private eventManager: ArteEventManager,
        private configService: ConfigService
    ) {
        this.instance = this;
    }

    ngOnInit() {
        this.isSaving = false;
        if (this.activatedRoute.snapshot.data['operation']) {
            this.operation = this.activatedRoute.snapshot.data['operation'];
        } else {
            this.operation = new Operation();
        }
        this.initializeFormGroup();
        this.registerChangesOnOperation();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.updatesSubscription);
    }

    delete(): void {
        this.genericModalService.open(<any>OperationDeleteDialogComponent, { operation: this.operation }).result.subscribe((res) => {
            this.router.navigate(['/operation']);
        });
    }

    isEditMode(): boolean {
        const lastPath = this.activatedRoute.snapshot.url[this.activatedRoute.snapshot.url.length - 1].path;
        return lastPath === 'edit' || lastPath === 'new';
    }

    clear(): void {
        if (this.operation.id) {
            this.router.navigate(['/operation', this.operation.id]);
        } else {
            this.router.navigate(['/operation']);
        }
    }

    save(): void {
        this.isSaving = true;
        if (this.operation.id) {
            this.subscribeToSaveResponse(this.operationService.update(this.operation));
        } else {
            this.subscribeToSaveResponse(this.operationService.create(this.operation));
        }
    }

    publishInternally(): void {
        this.operationService.publishInternally(this.operation.id).subscribe(
            (res: Operation) => this.load(res));
    }

    publishExternally(): void {
        this.operationService.publishExternally(this.operation.id).subscribe(
            (res: Operation) => this.load(res));
    }

    getTitlesContainer(): ElementRef {
        return this.titlesContaner;
    }

    existsOperation(): boolean {
        return !!this.operation && !!this.operation.id;
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
        return this.operation.procStatus === ProcStatusEnum.DRAFT;
    }

    canPublishExternally(): boolean {
        return this.operation.procStatus === ProcStatusEnum.INTERNALLY_PUBLISHED;
    }

    private initializeFormGroup(): void {
        this.editForm = new FormGroup({
            code: new FormControl('', Validators.compose([TextValidator.required, TextValidator.maxLenghtShortText])),
            title: new FormControl('', [TextValidator.required, TextValidator.maxLenghtShortText])
        });
    }

    private registerChangesOnOperation(): void {
        this.updatesSubscription = this.eventManager.subscribe(OperationFormComponent.EVENT_NAME, (result) => {
            if (result.content !== 'saved') {
                this.load(result.content);
            }
        });
    }

    private load(res: Operation): void {
        this.operation = Object.assign({}, res);
    }

    private subscribeToSaveResponse(result: Observable<Operation>): void {
        result.subscribe(
            (res: Operation) => this.onSaveSuccess(res),
            (err) => this.onSaveError(err)
        );
    }

    private onSaveSuccess(result: Operation): void {
        this.isSaving = false;
        this.eventManager.broadcast({ name: OperationFormComponent.EVENT_NAME, content: 'saved' });
        this.router.navigate(['operation', result.id]);
    }

    private onSaveError(err: any): void {
        this.isSaving = false;
    }
}
