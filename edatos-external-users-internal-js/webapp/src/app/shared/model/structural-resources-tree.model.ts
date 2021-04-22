import { Type } from 'class-transformer';
import { InternationalString } from './international-string.model';

export class StructuralResourcesTree {
    public id: number;
    public code: string;
    public type: 'category' | 'operation';
    public subscribers = 0;

    @Type(() => InternationalString)
    public name: InternationalString;

    @Type(() => StructuralResourcesTree)
    public children: StructuralResourcesTree[] = [];

    public getLocalisedName(locale: string): string | undefined {
        return this.name.getLocalisedLabel(locale);
    }
}
