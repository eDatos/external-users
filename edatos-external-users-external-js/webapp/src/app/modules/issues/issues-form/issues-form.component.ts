import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Issues } from '@app/core/model';

@Component({
    selector: 'app-issues-form',
    templateUrl: './issues-form.component.html',
})

export class IssuesFormComponent implements OnInit {
    public issues: Issues;
    public account: any;

    
    constructor(
        //private issuesService: IssuesService,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        //private configService: ConfigService
    ) { }
    

    public ngOnInit() {
        this.account = this.activatedRoute.snapshot.data.user;
    }

    public submit(): void {
        // TODO
    }
}