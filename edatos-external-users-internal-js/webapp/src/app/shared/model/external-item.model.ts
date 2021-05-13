import { BaseVersionedEntity } from 'arte-ng/model';
import { Type, TypeOptions } from 'class-transformer';
import { InternationalString } from './international-string.model';

/**
 * This enum is a subset from the one located at
 * {@link https://git.arte-consultores.com/istac/edatos-core-common/blob/master/src/main/java/org/siemac/edatos/core/common/enume/TypeExternalArtefactsEnum.java|edatos-core-common}.
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

export class ExternalCategory extends ExternalItem {
    public nestedCode: string;
}

export class ExternalOperation extends ExternalItem {
    public externalCategoryUrn: string;
}

/**
 * This configuration object inform class-transformer how to instantiate the
 * ExternalItem to the right subclass, if necessary.
 */
export const EXTERNAL_ITEM_DISCRIMINATOR: TypeOptions = {
    discriminator: {
        property: 'type',
        subTypes: [
            { value: ExternalCategory, name: TypeExternalArtefacts.CATEGORY },
            { value: ExternalOperation, name: TypeExternalArtefacts.STATISTICAL_OPERATION },
        ],
    },
    keepDiscriminatorProperty: true,
};
