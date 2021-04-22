import { InternationalString } from './international-string.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';

export class Category extends BaseVersionedAndAuditingEntity {
    public code: string;
    public nestedCode: string | null = null;

    @Type(() => InternationalString)
    public name: InternationalString;

    @Type(() => Category)
    public parent: Category | null = null;

    @Type(() => Category)
    public children: Category[] = [];

    public get type(): 'category' {
        return 'category';
    }
}
