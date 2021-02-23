import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';

export class Filter extends BaseVersionedAndAuditingEntity {
    constructor(
        public id?: number,
        public name?: string,
        public resourceName?: string,
        public login?: string,
        public permalink?: string,
        public lastAccessDate?: Date,
        public notes?: string
    ) {
        super();
    }
}
