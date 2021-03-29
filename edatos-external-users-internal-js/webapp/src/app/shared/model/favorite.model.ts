import { Category } from './category.model';
import { Operation } from './operation.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';

export class Favorite extends BaseVersionedAndAuditingEntity {
    public id?: number;
    public email?: string;

    @Type(() => Category)
    public category?: Category;

    @Type(() => Operation)
    public operation?: Operation;

    public getLocalisedName(languageCode: string): string {
        return (this.category.name || this.operation.title)?.getLocalisedLabel(languageCode);
    }
}
