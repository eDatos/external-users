import { BaseEntity, BaseVersionedEntity } from 'arte-ng/model';
import { LocalisedString } from './localised-string.model';

export class InternationalString extends BaseVersionedEntity implements BaseEntity {
    constructor(public id?: number, public texts?: LocalisedString[]) {
        super();
    }

    public get(localeLabel: string): LocalisedString | undefined {
        return this.texts.find((locale) => locale.label === localeLabel);
    }
}
