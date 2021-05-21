import { EXTERNAL_ITEM_DISCRIMINATOR, ExternalItem, ExternalOperation } from '@app/shared/model/external-item.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Exclude, Type } from 'class-transformer';
import { InternationalString } from './international-string.model';

export class Category extends BaseVersionedAndAuditingEntity {
    @Exclude()
    public readonly favoriteType = 'category';

    public subscribers = 0;
    public index = 0;

    @Type(() => InternationalString)
    public name = new InternationalString();

    @Type(() => ExternalItem, EXTERNAL_ITEM_DISCRIMINATOR)
    public resources: ExternalItem[] = [];

    @Type(() => Category)
    public children: Category[] = [];

    @Type(() => ExternalOperation)
    public operations: ExternalOperation[] = [];
}
