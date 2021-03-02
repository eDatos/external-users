import { Component, OnInit } from '@angular/core';
import { ConfigService } from '@app/config';
import { ScriptService } from 'ngx-script-loader';

declare const MetamacNavBar: {
    loadNavbar(obj?: { element: string }): void;
};

@Component({
    selector: 'app-edatos-navbar',
    template: ` <nav id="edatos-navbar"></nav>`,
    styleUrls: ['./edatos-navbar.component.scss'],
})
export class EdatosNavbarComponent implements OnInit {
    constructor(private configService: ConfigService, private scriptService: ScriptService) {}

    ngOnInit() {
        const navbarScriptUrl = this.configService.getConfig().metadata.navbarScriptUrl;
        this.scriptService.loadScript(`${navbarScriptUrl}/js/metamac-navbar.js`).subscribe(
            (_) => {
                MetamacNavBar.loadNavbar({
                    element: 'edatos-navbar',
                });
            },
            (err) => {
                console.error('Error al obtener el navbar', err);
            }
        );
    }
}
