import { Type } from 'class-transformer';
import { LocalisedString } from './localised-string.model';

export class InternationalString {
    @Type(() => LocalisedString)
    public texts: LocalisedString[];

    public getLocalisedLabel(locale: string): string {
        return this.texts?.find((localisedString) => localisedString.locale === locale)?.label;
    }
}
