import { Category, ExternalItem, InternationalString } from '@app/shared';
import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';

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
    constructor(
        public id?: number,
        public code?: string,
        public urn?: string,
        public indicatorSystem?: boolean,
        public internalInventoryDate?: Date,
        public currentlyActive?: boolean,
        public inventoryDate?: Date,
        public commonMetadata?: ExternalItem,
        public title?: InternationalString,
        public acronym?: InternationalString,
        public subjectArea?: Category,
        public objective?: InternationalString,
        public description?: InternationalString,
        public procStatus?: ProcStatus,
        public status?: Status,
        public comment?: InternationalString,
        public notes?: InternationalString,
        public secondarySubjectAreas?: Category[],
        public producer?: ExternalItem[],
        public regionalResponsible?: ExternalItem[],
        public regionalContributor?: ExternalItem[],
        public publisher?: ExternalItem[],
        public updateFrequency?: ExternalItem[]
    ) {
        super();
    }
}
