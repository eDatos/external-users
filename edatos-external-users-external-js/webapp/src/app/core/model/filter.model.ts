import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { User } from './user.model';

export class Filter extends BaseVersionedAndAuditingEntity {
    public name: string;
    public resourceName: string | null = null;
    public permalink: string;
    public lastAccessDate: Date;
    public notes: string | null = null;

    @Type(() => User)
    public externalUser: User;
}
