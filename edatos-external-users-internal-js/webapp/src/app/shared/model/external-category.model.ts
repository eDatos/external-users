import { ExternalItem } from '@app/shared/model/external-item.model';
import { Type } from 'class-transformer';

export class ExternalCategory extends ExternalItem {
    public nestedCode: string;

    @Type(() => ExternalCategory)
    public children: ExternalCategory[];
}
