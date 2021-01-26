import { BaseVersionedEntity, BaseEntity } from 'arte-ng/src/lib/model';
import { LocalisedString } from './localised-string.model';

export class InternationalString extends BaseVersionedEntity implements BaseEntity {
    constructor(public id?: number, public texts?: LocalisedString[]) {
        super();
    }
}
