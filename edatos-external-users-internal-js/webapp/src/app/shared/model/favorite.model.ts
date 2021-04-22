import { ExternalUser } from '@app/core/model';
import { StructuralResourcesTree } from '@app/shared/model/structural-resources-tree.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { Category } from './category.model';
import { Operation } from './operation.model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    @Type(() => ExternalUser)
    public externalUser: ExternalUser;

    @Type(() => Category)
    public category: Category;

    @Type(() => Operation)
    public operation: Operation;

    public set resource(val: Category | Operation | StructuralResourcesTree) {
        if (val instanceof Category || (val instanceof StructuralResourcesTree && val.type === 'category')) {
            this.category = val as Category;
        } else if (val instanceof Operation || (val instanceof StructuralResourcesTree && val.type === 'operation')) {
            this.operation = val as Operation;
        } else {
            console.error("Favorite resource needs a Category or an Operation, but the argument isn't an instance of neither");
        }
    }

    public get resource(): Category | Operation | StructuralResourcesTree {
        return this.category ?? this.operation;
    }

    public getLocalisedName(languageCode: string): string {
        return (this.category || this.operation).name?.getLocalisedLabel(languageCode);
    }
}
