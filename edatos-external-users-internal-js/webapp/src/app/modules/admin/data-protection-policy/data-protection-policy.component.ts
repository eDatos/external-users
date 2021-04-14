import { AfterViewInit, Component, ViewChild, LOCALE_ID, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InternationalString } from '@app/shared/model/international-string.model';
import { DataProtectionPolicyService } from './data-protection-policy.service';

@Component({
  selector: 'app-data-protection-policy',
  templateUrl: './data-protection-policy.component.html',
  styleUrls: []
})
export class DataProtectionPolicyComponent {

  dataProtectionPolicy: InternationalString;
  isEditMode: boolean = false;
  dataProtectionPolicyForm: FormGroup;

  constructor(route: ActivatedRoute, private router: Router, private dataProtectionPolicyService: DataProtectionPolicyService, @Inject(LOCALE_ID) public locale: string, private fb: FormBuilder) {
    if (route.snapshot.url.length !== 0) {
      const lastPath = route.snapshot.url[route.snapshot.url.length - 1].path;
      this.isEditMode = lastPath === 'edit';
    }

    this.dataProtectionPolicyForm = this.fb.group({ 'dataProtectionPolicy': [[]] });
    this.dataProtectionPolicyService.getDataProtectionPolicy().subscribe(dataProtectionPolicy => {
      this.dataProtectionPolicy = new InternationalString();
      this.dataProtectionPolicy.id = dataProtectionPolicy.id;
      this.dataProtectionPolicy.texts = dataProtectionPolicy.value.texts;
      this.dataProtectionPolicyForm.get("dataProtectionPolicy").setValue(this.dataProtectionPolicy.texts);
    });
  }

  clear() {
    // const with arrays: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/const
    const returnPath = ['/admin/data-protection-policy'];
    this.router.navigate(returnPath);
  }

  getLocalisedDataProtectionPolicy(locale?: string): string {
    return this.dataProtectionPolicy.getLocalisedLabel(locale ? locale : this.locale);
  }

  save() {
    console.log(this.dataProtectionPolicyForm.value);
    this.dataProtectionPolicyService.updateDataProtectionPolicy(this.dataProtectionPolicyForm.get('dataProtectionPolicy').value).subscribe(() => {
      this.clear();
    }, (error) => {
      console.error(error);
    });
  }
}
