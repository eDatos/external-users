import { Component } from '@angular/core';
import { DEFAULT_LANG } from '@app/app.constants';
import { DataProtectionPolicy } from '@app/shared/model/data-protection-policy.model';
import { TranslateService } from '@ngx-translate/core';
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

  constructor(dataProtectionPolicyService: DataProtectionPolicyService, translateService: TranslateService) {
    this.locales = [translateService.getDefaultLang()];
    dataProtectionPolicyService.getDataProtectionPolicy().subscribe(dataProtectionPolicy => {
      this.dataProtectionPolicy = dataProtectionPolicy;
      if(!dataProtectionPolicy.value.getLocalisedLabel(this.locales[0])) {
        this.locales = [DEFAULT_LANG];
      }
    });
  }
}
