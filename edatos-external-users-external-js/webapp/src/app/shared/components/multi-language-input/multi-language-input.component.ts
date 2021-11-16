import { Component, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { InternationalString } from '@app/shared/model';

@Component({
    selector: 'app-multi-language-input',
    templateUrl: './multi-language-input.component.html',
    styleUrls: ['./multi-language-input.component.css'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            multi: true,
            useExisting: forwardRef(() => MultiLanguageInputComponent),
        },
    ],
})
export class MultiLanguageInputComponent implements ControlValueAccessor {
    onTouched: any = () => {};
    onChange: any = () => {};
    isDisabled: boolean = false;
    value: InternationalString = new InternationalString();
    mappedValue: any = {};

    @Input()
    locales: String[] = [];

    @Input()
    isEditMode: boolean = true;

    constructor() {
        this.value.texts = [];
    }

    writeValue(value: InternationalString): void {
        this.value = value || new InternationalString();
        this.value.texts = this.value.texts || [];
        this.mappedValue = {};
        this.value.texts.forEach((value) => {
            this.mappedValue[value.locale] = value.label;
        });
    }

    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    setDisabledState?(isDisabled: boolean): void {
        this.isDisabled = isDisabled;
    }

    onEditorChange(event: any, locale: string) {
        this.value.texts.map((val) => {
            if (val.locale === locale) val.label = event.currentTarget.value;
            return val;
        });
        this.mappedValue[locale] = event.currentTarget.value;
        this.onChange(this.value);
    }

    onEditorTouched(event: any) {
        this.onTouched();
    }
}
