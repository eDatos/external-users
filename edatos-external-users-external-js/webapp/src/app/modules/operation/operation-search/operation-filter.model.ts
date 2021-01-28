import { DatePipe } from '@angular/common';
import { BaseEntityFilter, EntityFilter } from 'arte-ng/src/lib/model';

export class OperationFilter extends BaseEntityFilter implements EntityFilter {
    public code: string;
    public title: string;

    constructor(public datePipe?: DatePipe) {
        super(datePipe);
    }

    protected registerParameters() {
        this.registerParam({
            paramName: 'code',
            updateFilterFromParam: (param) => (this.code = param),
            clearFilter: () => (this.code = null)
        });
        this.registerParam({
            paramName: 'title',
            updateFilterFromParam: (param) => (this.title = param),
            clearFilter: () => (this.title = null)
        });
    }

    getCriterias() {
        const criterias = [];
        if (this.code) {
            criterias.push(`CODE ILIKE '%${this.escapeSingleQuotes(this.code)}%'`);
        }
        if (this.title) {
            criterias.push(`TITLE ILIKE '%${this.escapeSingleQuotes(this.title)}%'`);
        }
        return criterias;
    }
}
