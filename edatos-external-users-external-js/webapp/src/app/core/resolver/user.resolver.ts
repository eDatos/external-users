import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../model';
import { AccountUserService } from '../service';

@Injectable({ providedIn: 'root' })
export class UserResolver implements Resolve<User> {
    constructor(private userService: AccountUserService) {}

    resolve(route: ActivatedRouteSnapshot): Observable<User> {
        return this.userService.getLogueado();
    }
}
