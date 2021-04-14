import { Component, forwardRef, Input, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => EditorComponent),
    }
  ]
})
export class EditorComponent implements ControlValueAccessor {

  quillEditor: any;
  isDisabled: boolean;
  value: string;
  onChange: any;
  onTouched: any;

  @Input()
  isEditMode: boolean = true;

  constructor() { }

  writeValue(value: string): void {
    this.value = value;
    this.quillEditor?.setText(value);
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
    isDisabled || !this.isEditMode ? this.quillEditor.disable() : this.quillEditor.enable();
  }

  onEditorInit(event: any) {
    this.quillEditor = event.editor;
    this.isDisabled || !this.isEditMode ? event.editor.disable() : event.editor.enable();
    event.editor.setText(this.value);
  }

  onEditorChange(event: any) {
    this.value = event.textValue;
    this.onChange(this.value);
    this.onTouched();
  }
}
