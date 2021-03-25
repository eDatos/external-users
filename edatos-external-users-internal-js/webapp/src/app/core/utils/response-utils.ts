import { HttpResponse } from '@angular/common/http';
import { ResponseWrapper } from 'arte-ng/model';
import { plainToClass } from 'class-transformer';
import { OperatorFunction } from 'rxjs';
import { map } from 'rxjs/operators';

export function mapPlainToResponseWrapper<T>(clazz: new () => T): OperatorFunction<HttpResponse<T>, ResponseWrapper> {
    return map((response) => convertToResponseWrapper(response, clazz));
}

export function mapPlainToClass<T>(clazz: new () => T): OperatorFunction<T, T> {
    return map((element) => convert(element, clazz));
}

export function convert<T>(element: T, clazz: new () => T): T {
    return plainToClass(clazz, element);
}

export function convertToResponseWrapper<T>(response: HttpResponse<T>, clazz: new () => T) {
    const { headers, body, status } = response;
    const data = Array.isArray(body) ? body.map((element) => convert(element, clazz)) : convert(body, clazz);
    return new ResponseWrapper(headers, data, status);
}
