import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { User } from './user.model';

export class Filter extends BaseVersionedAndAuditingEntity {
    public id?: number;
    public name?: string;
    public resourceName?: string;

    @Type(() => User)
    public externalUser?: User;

    public permalink?: string;
    public lastAccessDate?: Date;
    public notes?: string;
}
