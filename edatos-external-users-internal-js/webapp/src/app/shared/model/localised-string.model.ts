import { BaseVersionedEntity, BaseEntity } from 'arte-ng/model';

export class LocalisedString extends BaseVersionedEntity implements BaseEntity {
    constructor(public id?: number, public label?: string, public locale?: string, public isUnmodifiable?: Boolean) {
        super();
    }
}
