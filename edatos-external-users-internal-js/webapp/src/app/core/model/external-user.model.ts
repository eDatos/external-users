import { Language } from '@app/core/model/language.model';
import { Treatment } from '@app/core/model/treatment.model';
import { BaseAuditingEntity } from 'arte-ng/model';

export class ExternalUser extends BaseAuditingEntity {
    constructor(
        public id?: any,
        public name?: string,
        public surname1?: string,
        public surname2?: string,
        public password?: string,
        public email?: string,
        public organism?: string,
        public treatment?: Treatment,
        public language?: Language,
        public phoneNumber?: string,
        public roles?: any[]
    ) {
        super();
    }
}
