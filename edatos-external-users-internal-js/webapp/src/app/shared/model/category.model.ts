import { EXTERNAL_ITEM_DISCRIMINATOR, ExternalItem } from '@app/shared/model/external-item.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { InternationalString } from './international-string.model';

export class Category extends BaseVersionedAndAuditingEntity {
    public subscribers = 0;

    public index = 0;

    @Type(() => InternationalString)
    public name = new InternationalString();

    @Type(() => ExternalItem, EXTERNAL_ITEM_DISCRIMINATOR)
    public resources: ExternalItem[] = [];

    @Type(() => Category)
    public children: Category[] = [];
}
