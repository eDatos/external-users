import { ExternalUser } from '@app/core/model';
import { ExternalOperation } from '@app/shared/model/external-item.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { Category } from './category.model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    @Type(() => ExternalUser)
    public externalUser: ExternalUser;

    @Type(() => ExternalOperation)
    private operation: ExternalOperation | null;

    @Type(() => Category)
    private category: Category | null;

    public get resource(): Category | ExternalOperation | undefined {
        return this.category || this.operation;
    }

    public set resource(resource: Category | ExternalOperation) {
        if (resource.favoriteType === 'category') {
            this.category = resource;
            this.operation = null;
        } else if (resource.favoriteType === 'operation') {
            this.operation = resource;
            this.category = null;
        } else {
            throw new Error('Unrecognized favorite type');
        }
    }

    public get favoriteType(): 'category' | 'operation' | undefined {
        return this.resource?.favoriteType;
    }

    public getLocalisedName(languageCode: string): string | undefined {
        return (this.category || this.operation)?.name?.getLocalisedLabel(languageCode);
    }
}
