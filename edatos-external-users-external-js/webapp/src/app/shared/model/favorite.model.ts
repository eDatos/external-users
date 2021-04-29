import { User } from '@app/core/model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { Category } from './category.model';
import { Operation } from './operation.model';
import { StructuralResourcesTree } from './structural-resources-tree.model';

export class Favorite extends BaseVersionedAndAuditingEntity {
    public id?: number;

    @Type(() => User)
    public externalUser?: User;

    @Type(() => Category)
    public category: Category | null = null;

    @Type(() => Operation)
    public operation: Operation | null = null;

    public set resource(val: Category | Operation | StructuralResourcesTree | null) {
        if (val instanceof Category || (val instanceof StructuralResourcesTree && val.type === 'category')) {
            this.category = val as Category;
        } else if (val instanceof Operation || (val instanceof StructuralResourcesTree && val.type === 'operation')) {
            this.operation = val as Operation;
        } else {
            console.error("Favorite resource needs a Category or an Operation, but the argument isn't an instance of neither");
        }
    }

    public get resource(): Category | Operation | StructuralResourcesTree | null {
        return this.category ?? this.operation;
    }

    public getLocalisedName(languageCode: string): string | undefined {
        return (this.category || this.operation).name?.getLocalisedLabel(languageCode);
    }
}
