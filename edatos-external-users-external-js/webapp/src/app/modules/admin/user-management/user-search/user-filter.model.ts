import { DatePipe } from '@angular/common';
import { EntityFilter, BaseEntityFilter } from 'arte-ng/src/lib/model';
import { Role } from '@app/core/model';

export class UserFilter extends BaseEntityFilter implements EntityFilter {
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
            clearFilter: () => (this.name = null)
        });
        this.registerParam({
            paramName: 'rol',
            updateFilterFromParam: (param) => (this.rol = this.convertParamToRole(param)),
            clearFilter: () => (this.rol = null)
        });
        this.registerParam({
            paramName: 'includeDeleted',
            updateFilterFromParam: (param) => {
                this.includeDeleted = param === 'true';
            },
            clearFilter: () => (this.includeDeleted = false)
        });
    }

    getCriterias() {
        const criterias = [];
        if (this.name) {
            criterias.push(`USUARIO ILIKE '%${this.escapeSingleQuotes(this.name)}%'`);
        }
        if (this.rol) {
            criterias.push(`ROLE EQ '${this.rol}'`);
        }
        if (!this.includeDeleted) {
            criterias.push(`DELETION_DATE IS_NULL`);
        }
        return criterias;
    }

    private convertParamToRole(param: any): Role {
        const currentKey = this.allRoles.find((key) => key === param);
        return currentKey ? Role[currentKey] : undefined;
    }
}
