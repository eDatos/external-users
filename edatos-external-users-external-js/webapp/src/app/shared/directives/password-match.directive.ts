import { Directive, Input } from '@angular/core';
import { FormGroup, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';
import { passwordMatchValidator } from '../validators/password-match.validator';

@Directive({
    selector: '[passwordMatch]',
    providers: [{ provide: NG_VALIDATORS, useExisting: PasswordMatchValidatorDirective, multi: true }],
})
export class PasswordMatchValidatorDirective implements Validator {
    @Input('passwordMatch') fields: string[] = [];

    validate(formGroup: FormGroup): ValidationErrors {
        return passwordMatchValidator(this.fields[0], this.fields[1])(formGroup);
    }
}
