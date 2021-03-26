import { InternationalString } from '@app/shared';

export function getLocalisedLabel(internationalString: InternationalString, locale: string): string {
    return internationalString.texts.find((localisedString) => localisedString.locale === locale).label;
}
