import { Category } from '@app/shared/model/category.model';
import { InternationalString } from '@app/shared/model/international-string.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
export class Operation extends BaseVersionedAndAuditingEntity {
    public id?: number;

    public code?: string;

    @Type(() => InternationalString)
    public name?: InternationalString;

    @Type(() => Category)
    public category?: Category;

    public get type(): 'operation' {
        return 'operation';
    }
}
