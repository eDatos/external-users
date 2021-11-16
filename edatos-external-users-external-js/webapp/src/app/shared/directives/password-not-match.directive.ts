import { Directive, Input } from '@angular/core';
import { FormGroup, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';
import { passwordNotMatchValidator } from '../validators/password-match.validator';

@Directive({
    selector: '[passwordNotMatch]',
    providers: [{ provide: NG_VALIDATORS, useExisting: PasswordNotMatchValidatorDirective, multi: true }],
})
export class PasswordNotMatchValidatorDirective implements Validator {
    @Input('passwordNotMatch') fields: string[] = [];

    validate(formGroup: FormGroup): ValidationErrors {
        return passwordNotMatchValidator(this.fields[0], this.fields[1])(formGroup);
    }
}
