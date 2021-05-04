import { InternationalString } from './international-string.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';

export class Category extends BaseVersionedAndAuditingEntity {
    public id?: number;
    public code?: string;
    public nestedCode?: string;

    public uri?: string;
    public urn?: string;

    @Type(() => InternationalString)
    public name?: InternationalString;

    @Type(() => Category)
    public parent?: Category;

    @Type(() => Category)
    public children?: Category[];

    public get type(): 'category' {
        return 'category';
    }
}
