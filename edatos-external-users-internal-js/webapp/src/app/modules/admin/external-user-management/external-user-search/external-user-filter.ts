import { DatePipe } from '@angular/common';
import { EntityFilter, BaseEntityFilter } from 'arte-ng/model';
import { Role } from '@app/core/model';

export class ExternalUserFilter extends BaseEntityFilter implements EntityFilter {
    public name?: string;
    public includeDeleted: boolean;
    public rol: Role;
    public allRoles: string[];

    constructor(public datePipe?: DatePipe) {
        super(datePipe);
        this.allRoles = Object.keys(Role);
    }

    protected registerParameters() {
        this.registerParam({
            paramName: 'name',
            updateFilterFromParam: (param) => (this.name = param),
            clearFilter: () => (this.name = null),
        });
        this.registerParam({
            paramName: 'includeDeleted',
            updateFilterFromParam: (param) => {
                this.includeDeleted = param === 'true';
            },
            clearFilter: () => (this.includeDeleted = false),
        });
    }

    public getCriterias() {
        const criterias = [];
        if (this.name) {
            criterias.push(`USUARIO ILIKE '%${this.escapeSingleQuotes(this.name)}%'`);
        }
        if (!this.includeDeleted) {
            criterias.push(`DELETION_DATE IS_NULL`);
        }
        return criterias;
    }
}
