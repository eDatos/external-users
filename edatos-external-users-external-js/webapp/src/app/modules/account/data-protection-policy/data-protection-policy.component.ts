import { Component, LOCALE_ID, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DataProtectionPolicyService } from './data-protection-policy.service';

@Component({
  selector: 'app-data-protection-policy',
  templateUrl: './data-protection-policy.component.html',
  styleUrls: []
})
export class DataProtectionPolicyComponent {

  isEditMode: boolean = false;
  locales: string[] = [];
  dataProtectionPolicyForm: FormGroup;

  constructor(dataProtectionPolicyService: DataProtectionPolicyService, @Inject(LOCALE_ID) locale: string, fb: FormBuilder) {
    this.dataProtectionPolicyForm = fb.group({ 'dataProtectionPolicy': [[]] });
    this.locales = [locale];
    dataProtectionPolicyService.getDataProtectionPolicy().subscribe(dataProtectionPolicy => {
      this.dataProtectionPolicyForm.get("dataProtectionPolicy").setValue(dataProtectionPolicy.value.texts);
    });
  }
}
