import { Component, OnInit } from '@angular/core';
import { ERROR_ALERT_KEY } from '@app/app.constants';
import { ConfigService } from '@app/config';

declare const MetamacNavBar: {
    loadNavbar(obj?: { element: string }): void;
};

@Component({
    selector: 'app-edatos-navbar',
    templateUrl: './edatos-navbar.component.html',
    styleUrls: ['./edatos-navbar.component.scss'],
})
export class EdatosNavbarComponent implements OnInit {
    public errorAlertKey = ERROR_ALERT_KEY;

    constructor(private configService: ConfigService) {}

    ngOnInit() {
        const navbarScriptUrl = this.configService.getConfig().metadata.navbarScriptUrl;
        this.loadScript(`${navbarScriptUrl}/js/metamac-navbar.js`).then(
            (_) => {
                MetamacNavBar.loadNavbar({
                    element: 'edatos-navbar',
                });
            },
            (err) => {
                // TODO: preguntar qué hacer;
                console.error('Error al obtener el navbar', err);
            }
        );
    }

    private loadScript(dynamicScript) {
        return new Promise((resolve, reject) => {
            const scriptEle = document.createElement('script');
            scriptEle.onload = resolve;
            scriptEle.onerror = reject;
            scriptEle.src = dynamicScript;
            scriptEle.type = 'text/javascript';
            scriptEle.async = false;
            scriptEle.charset = 'utf-8';
            document.getElementsByTagName('head')[0].appendChild(scriptEle);
        });
    }
}
