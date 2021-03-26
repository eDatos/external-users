import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { plainToClass } from 'class-transformer';
import { OperatorFunction } from 'rxjs';
import { map } from 'rxjs/operators';

export class ResponseWrapper<T> {
    constructor(public headers: HttpHeaders, public body: T, public status: number) {}
}

export function mapPlainToClass<T>(clazz: new () => T): OperatorFunction<T, T>;
export function mapPlainToClass<T>(clazz: new () => T): OperatorFunction<HttpResponse<T>, ResponseWrapper<T>>;
export function mapPlainToClass<T>(clazz: new () => T): OperatorFunction<HttpResponse<T[]>, ResponseWrapper<T[]>>;
export function mapPlainToClass<T>(clazz: new () => T): OperatorFunction<T | T[] | HttpResponse<T>, T | T[] | ResponseWrapper<T>> {
    return map((obj) => convert(clazz, obj));
}

export function convert<T>(clazz: new () => T, plain: any): T;
export function convert<T>(clazz: new () => T, plain: HttpResponse<any>): ResponseWrapper<T>;
export function convert<T>(clazz: new () => T, plain: HttpResponse<any[]>): ResponseWrapper<T[]>;
export function convert<T>(clazz: new () => T, obj: any | any[] | HttpResponse<any | any[]>): T | T[] | ResponseWrapper<T | T[]> {
    if (Array.isArray(obj)) {
        return convertToList(clazz, obj);
    }
    if (obj instanceof HttpResponse) {
        return convertToResponseWrapper(clazz, obj);
    }
    return plainToClass(clazz, obj);
}

function convertToList<T>(clazz: { new (): T }, list: any[]): T[] {
    return list.map((element) => plainToClass(clazz, element));
}

function convertToResponseWrapper<T>(clazz: new () => T, response: HttpResponse<T | T[]>): ResponseWrapper<T | T[]> {
    const { headers, body, status } = response;
    const data = Array.isArray(body) ? convertToList(clazz, body) : plainToClass(clazz, body);
    return new ResponseWrapper(headers, data, status);
}
