import { BaseVersionedEntity, BaseEntity } from 'arte-ng/model';
import { LocalisedString } from './localised-string.model';
import { Type } from 'class-transformer';

export class InternationalString extends BaseVersionedEntity implements BaseEntity {
    public id?: number;

    @Type(() => LocalisedString)
    public texts?: LocalisedString[] = [];

    public getLocalisedLabel(locale: string): string | undefined {
        return this.texts?.find((localisedString) => localisedString.locale === locale)?.label;
    }
}
