
import { Category, Operation } from '@app/shared';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    constructor(
        public id?: number,
        public login?: string,
        public category?: Category,
        public operation?: Operation,
    ) {
        super();
    }
}
