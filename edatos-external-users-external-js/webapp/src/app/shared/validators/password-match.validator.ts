import { FormGroup } from '@angular/forms';

export function passwordMatchValidator(controlName: string, matchingControlName: string) {
    return function (formGroup: FormGroup) {
        const control = formGroup.controls[controlName];
        const matchingControl = formGroup.controls[matchingControlName];

        if (!control || !matchingControl) {
            return null;
        }

        if (matchingControl.errors && !matchingControl.errors.passwordMatch) {
            return null;
        }

        if (control.value !== matchingControl.value) {
            matchingControl.setErrors({ passwordMatch: true });
        } else {
            matchingControl.setErrors(null);
        }
    };
}

export function passwordNotMatchValidator(controlName: string, matchingControlName: string) {
    return function (formGroup: FormGroup) {
        const control = formGroup.controls[controlName];
        const matchingControl = formGroup.controls[matchingControlName];

        if (!control || !matchingControl) {
            return null;
        }

        if (matchingControl.errors && !matchingControl.errors.passwordNotMatch) {
            return null;
        }

        if (control.value === matchingControl.value) {
            matchingControl.setErrors({ passwordNotMatch: true });
        } else {
            matchingControl.setErrors(null);
        }
    };
}
