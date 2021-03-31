import { Category } from '@app/shared/model/category.model';
import { InternationalString } from '@app/shared/model/international-string.model';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';

export enum ProcStatus {
    INTERNALLY_PUBLISHED = 'INTERNALLY_PUBLISHED',
    EXTERNALLY_PUBLISHED = 'EXTERNALLY_PUBLISHED',
}

export enum Status {
    PLANNING = 'PLANNING',
    DESIGN = 'DESIGN',
    PRODUCTION = 'PRODUCTION',
    OUT_OF_PRINT = 'OUT_OF_PRINT',
}

export class Operation extends BaseVersionedAndAuditingEntity {
    public id?: number;

    public code?: string;

    public currentlyActive?: boolean;

    @Type(() => InternationalString)
    public name?: InternationalString;

    @Type(() => InternationalString)
    public description?: InternationalString;

    @Type(() => Category)
    public category?: Category;

    public procStatus?: ProcStatus;
    public status?: Status;
}
