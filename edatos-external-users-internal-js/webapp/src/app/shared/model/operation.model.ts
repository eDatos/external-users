import { BaseAuditingEntity } from 'arte-ng/model';
import { InternationalString } from './international-string.model';
import { ProcStatusEnum } from './proc-status-enum.model';

export class Operation extends BaseAuditingEntity {

    constructor(public id?: number,
        public code?: string,
        public title?: InternationalString,
        public procStatus?: ProcStatusEnum,
        public urn?: string,
        public description?: string,
        public internalInventoryDate?: Date,
        public inventoryDate?: Date) {
        super();
    }
}