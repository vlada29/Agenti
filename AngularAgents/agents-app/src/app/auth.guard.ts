import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { UserServiceService } from './user-service.service';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private user: UserServiceService, private router:Router){} //session
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    if(!this.user.getLoggedIn()){
        this.router.navigate(['']);
        return false;
    }

    return this.user.getLoggedIn();
  }
}
