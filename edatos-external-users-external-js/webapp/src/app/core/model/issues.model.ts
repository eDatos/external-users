import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';

export class Issues extends BaseVersionedAndAuditingEntity {
    public id?: any;
    public name?: string;
    public surname1?: string;
    public surname2?: string;
    public password?: string;
    public email?: string;
}