
import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ControlValueAccessor, FormBuilder, FormGroup, NG_VALUE_ACCESSOR } from '@angular/forms';
import { InternationalString, LocalisedString } from '@app/shared/model';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-multi-language-editor',
  templateUrl: './multi-language-editor.component.html',
  styleUrls: ['./multi-language-editor.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => MultiLanguageEditorComponent),
    }
  ]
})
export class MultiLanguageEditorComponent implements ControlValueAccessor, OnChanges {

  @Input()
  locales: String[] = [];

  @Input()
  isEditMode: boolean = true;

  value: InternationalString = new InternationalString();
  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({});
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.locales.isFirstChange() && changes.locales?.currentValue && changes.locales.currentValue != changes.locales.previousValue) {
      this.form = this.fb.group(changes.locales.currentValue.reduce((acc, locale) => { acc[locale] = [this.value.getLocalisedLabel(locale || '') || '']; return acc; }, {}));
    }
  }

  writeValue(values: LocalisedString[]): void {
    values.forEach(value => this.form.get(value.locale).setValue(value.label));
    this.value.texts = values;
  }

  registerOnChange(fn: any): void {
    this.form.valueChanges.subscribe(value => {
      this.value.texts = this.locales.map(locale => ({ locale: locale, label: value[locale.toString()] } as LocalisedString));
      fn(this.value);
    });
  }


  registerOnTouched(fn: any): void {
    this.form.valueChanges.subscribe(value => {
      fn();
    });
  }

  setDisabledState?(isDisabled: boolean): void {
    isDisabled ? this.form.disable() : this.form.enable();
  }
}
/* TODO: eliminar el comentario si procede

import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ControlValueAccessor, FormBuilder, FormGroup, NG_VALUE_ACCESSOR } from '@angular/forms';
import { InternationalString, LocalisedString } from '@app/shared/model';
import { TranslateService } from '@ngx-translate/core';
import { SelectItem } from 'primeng/api';

@Component({
  selector: 'app-multi-language-editor',
  templateUrl: './multi-language-editor.component.html',
  styleUrls: ['./multi-language-editor.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => MultiLanguageEditorComponent),
    }
  ]
})
export class MultiLanguageEditorComponent implements ControlValueAccessor, OnChanges {

  @Input()
  locales: String[] = null;

  @Input()
  isEditMode: boolean = true;

  currentLocale: string;
  quillEditors: any[] = [];
  value: InternationalString = new InternationalString();
  onTouched: any;
  onChange: any;
  isDisabled: boolean = false;
  languages: SelectItem[] = [];

  constructor(private translateService: TranslateService) {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.locales && changes.locales.isFirstChange() && changes.locales.currentValue && changes.locales.currentValue != changes.locales.previousValue) {
      this.translateService.get("enum.localeToLanguage").subscribe(values => {
        changes.locales.currentValue?.forEach(locale => {
          this.languages.push({ label: values[locale.toUpperCase()], value: locale } as SelectItem);
        });
      });
    }
  }

  writeValue(values: LocalisedString[]): void {
    this.value.texts = values;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
    this.quillEditors.forEach(quill => {
      isDisabled || !this.isEditMode ? quill.disable() : quill.enable();
    });
  }

  onLocaleChange(locale: any) {
    this.currentLocale = locale.value;
  }

  onEditorInit(event: any, locale: string) {
    this.quillEditors.push(event.editor);
    this.isDisabled || !this.isEditMode ? event.editor.disable() : event.editor.enable();
    event.editor.setText(this.value.getLocalisedLabel(locale) || "");
  }

  onEditorChange(event: any) {
    this.value.texts = this.value.texts.map(text => {
      if (text.locale === this.currentLocale) text.label = event.textValue;
      return text;
    });
    this.onChange(this.value);
    this.onTouched();
  }
}
*/