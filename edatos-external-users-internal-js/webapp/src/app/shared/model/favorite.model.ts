import { ExternalUser } from '@app/core/model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { Category } from './category.model';
import { Operation } from './operation.model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    public id?: number;

    @Type(() => ExternalUser)
    public externalUser?: ExternalUser;

    @Type(() => Category)
    public category?: Category;

    @Type(() => Operation)
    public operation?: Operation;

    public getLocalisedName(languageCode: string): string {
        return (this.category.name || this.operation.name)?.getLocalisedLabel(languageCode);
    }

    public getResource(): Category | Operation {
        return this.category ?? this.operation;
    }
}
