import { InternationalString } from '@app/shared/model/international-string.model';

/**
 * See also org.siemac.edatos.core.common.enume.TypeExternalArtefactsEnum at
 * https://git.arte-consultores.com/istac/edatos-core-common.
 *
 * Ref:
 * https://git.arte-consultores.com/istac/edatos-core-common/blob/master/src/main/java/org/siemac/edatos/core/common/enume/TypeExternalArtefactsEnum.java
 */
enum TypeExternalArtefacts {
    AGENCY = 'structuralResources#agency',
    AGENCY_SCHEME = 'structuralResources#agencyScheme',
    CATEGORY = 'structuralResources#category',
    CATEGORY_SCHEME = 'structuralResources#categoryScheme',
    CODELIST = 'structuralResources#codelist',
    CODE = 'structuralResources#code',
    CONCEPT_SCHEME = 'structuralResources#conceptScheme',
    CONCEPT = 'structuralResources#concept',
    CONTENT_CONSTRAINTS = 'structuralResources#contentConstraint',
    CONFIGURATION = 'commonMetadata#configuration',
    DATA_CONSUMER = 'structuralResources#dataConsumer',
    DATA_CONSUMER_SCHEME = 'structuralResources#dataConsumerScheme',
    DATA_PROVIDER = 'structuralResources#dataProvider',
    DATA_PROVIDER_SCHEME = 'structuralResources#dataProviderScheme',
    DATASTRUCTURE = 'structuralResources#dataStructure',
    DIMENSION = 'structuralResources#dimension',
    INDICATOR = 'indicators#indicator',
    ORGANISATION = 'structuralResources#organisation',
    ORGANISATION_SCHEME = 'structuralResources#organisationScheme',
    ORGANISATION_UNIT = 'structuralResources#organisationUnit',
    ORGANISATION_UNIT_SCHEME = 'structuralResources#organisationUnitScheme',
    STATISTICAL_OPERATION = 'statisticalOperations#operation',
    STATISTICAL_OPERATION_INSTANCE = 'statisticalOperations#instance',
    DATASET = 'statisticalResources#dataset',
    COLLECTION = 'statisticalResources#collection',
    QUERY = 'statisticalResources#query',
    MULTIDATASET = 'statisticalResources#multidataset',
}

export class ExternalItem {
    constructor(
        public id?: number,
        public uuid?: string,
        public optLock?: number,
        public code?: string,
        public codeNested?: string,
        public urn?: string,
        public title?: InternationalString,
        public urnProvider?: string,
        public type?: TypeExternalArtefacts,
        public uri?: string,
        public managementAppUrl?: string
    ) {}
}
