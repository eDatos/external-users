import { User } from '@app/core/model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { Category } from './category.model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    @Type(() => User)
    public externalUser?: User;

    @Type(() => Category)
    public category: Category | null = null;

    public getLocalisedName(languageCode: string): string | undefined {
        return this.category?.name?.getLocalisedLabel(languageCode);
    }
}
