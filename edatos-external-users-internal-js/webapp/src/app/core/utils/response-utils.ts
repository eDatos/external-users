import { HttpResponse } from '@angular/common/http';
import { ResponseWrapper } from 'arte-ng/model';
import { plainToClass } from 'class-transformer';

export function convert<T>(element: T, clazz: new () => T) {
    return plainToClass(clazz, element);
}

export function convertToResponseWrapper<T>(response: HttpResponse<any>, clazz: new () => T) {
    const { headers, body, status } = response;
    const data = Array.isArray(body) ? body.map((element) => convert(element, clazz)) : this.convert(body);
    return new ResponseWrapper(headers, data, status);
}
