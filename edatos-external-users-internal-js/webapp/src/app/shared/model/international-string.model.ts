import { BaseEntity, BaseVersionedEntity } from 'arte-ng/model';
import { Type } from 'class-transformer';
import { LocalisedString } from './localised-string.model';

export class InternationalString extends BaseVersionedEntity implements BaseEntity {
    public id?: number;

    @Type(() => LocalisedString)
    public texts?: LocalisedString[];

    public getLocalisedLabel(locale: string): string {
        return this.texts?.find((localisedString) => localisedString.locale === locale)?.label;
    }
}
