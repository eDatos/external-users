import { HttpClient } from "@angular/common/http";
import { ElementRef } from "@angular/core";
import { convert } from "@app/core/utils/response-utils";
import { map } from "rxjs/operators";

/*
 * http: the instance of HttpClient used for the http requests 
 * el: the element (or its id) where the captcha is going to be drawn as its last child
 * resultClass: the class of the result of the request
 * relativeUrl: relative url of the request, equivalent to the first parameter of the HttpClient.post<>() method
 * body: the body of the request
 * 
 * This function return a Promise that is resolved when the captcha has been solved correctly and the request has been successful.
 * If the captcha gets a wrong answer, then another one will appear without having to call this method again.
 * In other hand, that promise is rejected when the captcha has given an unexpected error or it has been solved correctly but the request was wrong. 
 */
export function postRequestWithCaptcha<T, G>(http: HttpClient, el: (ElementRef | string), resultClass: new () => G, relativeUrl: string, body: T): Promise<G> {
    const url = (new URL(relativeUrl, window.location.origin)).href;
    const requestMethod = (url: String): Promise<G> => {
        return http.post<G>(url.substr(window.location.origin.length), body).pipe(map((res) => convert(resultClass, res))).toPromise();
    }
    return requestWithCaptcha(
        requestMethod, 
        url, 
        (el instanceof ElementRef) ? { captchaEl: el.nativeElement } : { captchaId: el }
    );
}