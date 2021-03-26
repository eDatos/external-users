import { Category, InternationalString, Operation } from '@app/shared';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    constructor(public id?: number, public email?: string, public category?: Category, public operation?: Operation) {
        super();
    }

    public getName(): InternationalString {
        return this.category.name || this.operation.title;
    }
}
