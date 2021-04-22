import { InternationalString } from './international-string.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';

export class Category extends BaseVersionedAndAuditingEntity {
    public id?: number;

    public code?: string;

    public nestedCode?: string;

    public uri?: string;

    public urn?: string;

    public subscribedUsers?: number;

    @Type(() => InternationalString)
    public name?: InternationalString;

    @Type(() => InternationalString)
    public description?: InternationalString;

    @Type(() => InternationalString)
    public comment?: InternationalString;

    public updateDate?: Date;

    @Type(() => Category)
    public parent?: Category;

    @Type(() => Category)
    public children?: Category[];
}
