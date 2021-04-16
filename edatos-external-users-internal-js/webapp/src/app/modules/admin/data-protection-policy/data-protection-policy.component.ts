import { Component, LOCALE_ID, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InternationalString } from '@app/shared/model/international-string.model';
import { DataProtectionPolicyService } from './data-protection-policy.service';

@Component({
  selector: 'app-data-protection-policy',
  templateUrl: './data-protection-policy.component.html',
  styleUrls: []
})
export class DataProtectionPolicyComponent {

  dataProtectionPolicy: InternationalString = new InternationalString();
  isEditMode: boolean = false;
  locales: string[] = [];

  constructor(route: ActivatedRoute, private router: Router, private dataProtectionPolicyService: DataProtectionPolicyService, @Inject(LOCALE_ID) public locale: string) {
    if (route.snapshot.url.length !== 0) {
      const lastPath = route.snapshot.url[route.snapshot.url.length - 1].path;
      this.isEditMode = lastPath === 'edit';
    }

    this.dataProtectionPolicyService.getDataProtectionPolicy().subscribe(dataProtectionPolicy => {
      this.dataProtectionPolicy = dataProtectionPolicy;
      this.locales = this.dataProtectionPolicy.texts.map(text => text.locale);
    });
  }

  clear() {
    // const with arrays: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/const
    const returnPath = ['/admin/data-protection-policy'];
    this.router.navigate(returnPath);
  }

  save() {
    this.dataProtectionPolicyService.updateDataProtectionPolicy(this.dataProtectionPolicy).subscribe(() => {
      this.clear();
    });
  }
}
