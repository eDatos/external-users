import { BaseVersionedAndAuditingEntity } from 'arte-ng/model';

export class Issues extends BaseVersionedAndAuditingEntity {
    public id?: any;
    public name?: string;
    public surname1?: string;
    public surname2?: string;
    public subject?: string;
    public message?: string;
    public email?: string;
    public workplace?: WorkplaceEnum;
}

export enum WorkplaceEnum {
    UNIVERSIDAD = 'UNIVERSIDAD',
	OTROS = 'OTROS',
	ASOCIACIONES_EMPRESARIALES = 'ASOCIACIONES_EMPRESARIALES',
	EMPRENDEDOR = 'EMPRENDEDOR',
	ESTUDIANTE = 'ESTUDIANTE',
	MEDIO_COMUNICACION = 'MEDIO_COMUNICACION',
	ORGANISMO_PUBLICO = 'ORGANISMO_PUBLICO',
	PARTICULAR = 'PARTICULAR'
}