import { BaseVersionedEntity, BaseEntity } from 'arte-ng/model';
import { LocalisedString } from './localised-string.model';

export class InternationalString extends BaseVersionedEntity implements BaseEntity {
    constructor(public id?: number, public texts?: LocalisedString[]) {
        super();
    }

    public getLocalisedLabel(locale: string): string {
        return this.texts?.find((localisedString) => localisedString.locale === locale)?.label;
    }
}
