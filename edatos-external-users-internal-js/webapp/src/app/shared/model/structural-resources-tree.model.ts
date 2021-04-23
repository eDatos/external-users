import { Type } from 'class-transformer';
import { InternationalString } from './international-string.model';

export class StructuralResourcesTree {
    public id: number;

    public code: string;

    @Type(() => InternationalString)
    public name: InternationalString;

    public type: 'category' | 'operation';

    public subscribers: number;

    @Type(() => StructuralResourcesTree)
    public children: StructuralResourcesTree[];

    public getLocalisedName(): string {
        return this.name.getLocalisedLabel('es');
    }
}
