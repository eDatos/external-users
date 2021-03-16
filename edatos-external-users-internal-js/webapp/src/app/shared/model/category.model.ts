import { InternationalString } from '@app/shared';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';

export class Category extends BaseVersionedAndAuditingEntity {
    constructor(
        public id?: number,
        public code?: String,
        public nestedCode?: String,
        public uri?: String,
        public urn?: String,
        public uuid?: string,
        public name?: InternationalString,
        public description?: InternationalString,
        public comment?: InternationalString,
        public updateDate?: Date,
        public parent?: Category,
        public children?: Category[]
    ) {
        super();
    }
}
