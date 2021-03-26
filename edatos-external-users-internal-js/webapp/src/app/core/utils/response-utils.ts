import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { plainToClass } from 'class-transformer';

export class ResponseWrapper<T> {
    constructor(public headers: HttpHeaders, public body: T, public status: number) {}
}

/**
 * Converts a plain object into an instance of a class,
 * The class has to mark with `@Type` its fields that are meant to be instantiated with data
 * from the plain object. For more info about this, please refer to
 * {@link https://github.com/typestack/class-transformer#working-with-nested-objects}.
 *
 * @param clazz the constructor of the class.
 * @param plain the object to be converted.
 * @return an instance of the class filled with the data from the plain object, including
 * nested fields to an arbitrary level if they were annotated in the class with `@Type`.
 *
 * @example
 * ```
 * class MySubclass {
 *     anotherKey: number;
 * }
 *
 * class MyClass {
 *     key: string;
 *
 *     @Type(() => MySubclass)
 *     nested: MySubclass;
 * }
 *
 * const json = {
 *     key: "value",
 *     nested: {
 *         anotherKey: 3,
 *     }
 * };
 *
 * const instantiatedObject = convert(MyClass, json);
 * console.log(instantiatedObject instanceof MyClass); // true
 * console.log(instantiatedObject.nested instanceof MySubclass); // true
 * ```
 */
export function convert<T>(clazz: { new (): T }, plain: T): T;

/**
 * Converts every plain object from the array into an instance of a class,.
 * The class has to mark with `@Type` its fields that are meant to be instantiated with data
 * from the plain object. For more info about this, please refer to
 * {@link https://github.com/typestack/class-transformer#working-with-nested-objects}.
 *
 * @param clazz the constructor of the class.
 * @param arrayOfPlains the array of objects to be converted.
 * @return an array with instances of the class filled with the data from the plain objects, including
 * nested fields to an arbitrary level if they were annotated in the class with `@Type`.
 */
export function convert<T>(clazz: { new (): T }, arrayOfPlains: T[]): T[];

/**
 * Converts a plain object that is contained in the HttpResponse into an instance of a class, and returns
 * the converted object wrapped in a ResponseWrapper.
 * The class to which the object is going to be converted has to mark with `@Type` the fields that are meant to be
 * instantiated, otherwise only the main object will be instantiated.
 * For more info about this, please refer to
 * {@link https://github.com/typestack/class-transformer#working-with-nested-objects}.

 * @param clazz the constructor of the class.
 * @param response the response provided by the HttpClient.
 * @return a ResponseWrapper containing the instantiated objects.
 *
 * @remarks This function is meant to be used with the HttpClient, when the request does come
 * in the form of a HttpResponse and not the body directly (for example, when the request
 * is made with `{observe: 'response'}`. See the example below.
 *
 * @example
 * ```
 * public find(id: number): Observable<ResponseWrapper<Favorite>> {
 *     return this.http
 *         .get<Favorite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
 *         .pipe(map((httpResponse: HttpResponse<Favorite>) => convert(Favorite, httpResponse)));
 * }
 * ```
 */
export function convert<T>(clazz: { new (): T }, response: HttpResponse<T>): ResponseWrapper<T>;

/**
 * Converts an array of plain objects that are contained in the HttpResponse into instances of a class, and returns
 * the array of converted objects wrapped in a ResponseWrapper.
 * The class to which the objects are going to be converted has to mark with `@Type` the fields that are meant to be
 * instantiated, otherwise only the main object will be instantiated.
 * For more info about this, please refer to
 * {@link https://github.com/typestack/class-transformer#working-with-nested-objects}.

 * @param clazz the constructor of the class.
 * @param response the response provided by the HttpClient.
 * @return a ResponseWrapper containing the array of instantiated objects.
 *
 * @remarks This function is meant to be used with the HttpClient, when the request does come
 * in the form of a HttpResponse and not the body directly (for example, when the request
 * is made with `{observe: 'response'}`. See the example below.
 *
 * @example
 * ```
 * public findAll(): Observable<ResponseWrapper<Favorite[]>> {
 *     return this.http
 *         .get<Favorite[]>(this.resourceUrl, { observe: 'response' })
 *         .pipe(map((httpResponse: HttpResponse<Favorite[]>) => convert(Favorite, httpResponse)));
 * }
 * ```
 */
export function convert<T>(clazz: { new (): T }, response: HttpResponse<T[]>): ResponseWrapper<T[]>;

export function convert<T>(clazz: { new (): T }, obj: T | T[] | HttpResponse<T | T[]>): T | T[] | ResponseWrapper<T | T[]> {
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

function convertToResponseWrapper<T>(clazz: { new (): T }, response: HttpResponse<T | T[]>): ResponseWrapper<T | T[]> {
    const { headers, body, status } = response;
    const data = Array.isArray(body) ? convertToList(clazz, body) : plainToClass(clazz, body);
    return new ResponseWrapper(headers, data, status);
}
