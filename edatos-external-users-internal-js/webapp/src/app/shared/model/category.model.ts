import { ExternalItem } from '@app/shared/model/external-item.model';
import { InternationalString } from './international-string.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';

export class Category extends BaseVersionedAndAuditingEntity {
    constructor() {
        super();
        this.name = new InternationalString();
    }

    public subscribers: number;

    @Type(() => InternationalString)
    public name: InternationalString;

    @Type(() => ExternalItem)
    public resources: ExternalItem[];

    @Type(() => Category)
    public children: Category[];
}
