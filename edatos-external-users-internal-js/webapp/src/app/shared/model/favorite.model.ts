import { ExternalUser } from '@app/core/model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { Category } from './category.model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    @Type(() => ExternalUser)
    public externalUser: ExternalUser;

    @Type(() => Category)
    public category: Category;

    public getLocalisedName(languageCode: string): string | undefined {
        return this.category?.name?.getLocalisedLabel(languageCode);
    }
}
