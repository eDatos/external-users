import { ExternalUser } from '@app/core/model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';

export class Filter extends BaseVersionedAndAuditingEntity {
    public id?: number;
    public name?: string;
    public resourceName?: string;

    @Type(() => ExternalUser)
    public externalUser?: ExternalUser;

    public permalink?: string;
    public lastAccessDate?: Date;
    public notes?: string;
}
