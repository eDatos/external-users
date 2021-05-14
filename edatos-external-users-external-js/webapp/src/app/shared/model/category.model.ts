import { InternationalString } from './international-string.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { ExternalItem, EXTERNAL_ITEM_DISCRIMINATOR } from './external-item.model';

export class Category extends BaseVersionedAndAuditingEntity {
    @Type(() => InternationalString)
    public name = new InternationalString();

    @Type(() => ExternalItem, EXTERNAL_ITEM_DISCRIMINATOR)
    public resources: ExternalItem[] = [];

    @Type(() => Category)
    public children: Category[] = [];
}
