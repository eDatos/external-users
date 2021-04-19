import { Component, LOCALE_ID, Inject } from '@angular/core';
import { DEFAULT_LANG } from '@app/app.constants';
import { InternationalString } from '@app/shared';
import { DataProtectionPolicy } from '@app/shared/model/data-protection-policy.model';
import { DataProtectionPolicyService } from './data-protection-policy.service';

@Component({
  selector: 'app-data-protection-policy',
  templateUrl: './data-protection-policy.component.html',
  styleUrls: []
})
export class DataProtectionPolicyComponent {

  dataProtectionPolicy: DataProtectionPolicy = new DataProtectionPolicy();
  isEditMode: boolean = false;
  locales: string[] = [];

  constructor(dataProtectionPolicyService: DataProtectionPolicyService, @Inject(LOCALE_ID) locale: string) {
    this.locales = [locale];
    dataProtectionPolicyService.getDataProtectionPolicy().subscribe(dataProtectionPolicy => {
      this.dataProtectionPolicy = dataProtectionPolicy;
      if(!dataProtectionPolicy.value.getLocalisedLabel(this.locales[0])) {
        this.locales = [DEFAULT_LANG];
      }
    });
  }
}
