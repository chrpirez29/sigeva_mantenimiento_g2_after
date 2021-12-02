import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {TokenService} from "../Service/token.service";

@Injectable({
  providedIn: 'root'
})

export class FuncionalidadesGuardService implements CanActivate {
  realRol: string = "";
  superAdmin: boolean = false;

  constructor(private tokenService: TokenService, private router: Router) {  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRol = route.data.expectedRol;
    const rol = this.tokenService.getRol();

    this.realRol = String(rol);
    if (this.superAdmin){
      this.realRol = "SuperAdmin";
    }

    if (this.superAdmin)
      return true;

    if (!this.tokenService.getToken() || expectedRol.indexOf(this.realRol) === -1){
      this.router.navigate(['/'])
      return false;
    }

    return true;

  }

}
