import { BaseVersionedEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { InternationalString } from './international-string.model';

/**
 * This enum is a subset from the one located at
 * {@link https://git.arte-consultores.com/istac/edatos-core-common/blob/master/src/main/java/org/siemac/edatos/core/common/enume/TypeExternalArtefactsEnum.java edatos-core-common}.
 */
export enum TypeExternalArtefacts {
    CATEGORY = 'structuralResources#category',
    STATISTICAL_OPERATION = 'statisticalOperations#operation',
}

export class ExternalItem extends BaseVersionedEntity {
    public code: string;
    public urn: string;

    @Type(() => InternationalString)
    public name: InternationalString;

    public type: TypeExternalArtefacts;

    public getLocalisedName(languageCode: string): string | undefined {
        return this.name?.getLocalisedLabel(languageCode);
    }
}
