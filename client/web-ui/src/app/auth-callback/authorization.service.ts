import { Injectable } from '@angular/core';
import { AuthService } from '@falcon-ng/tailwind';
import { Role } from '../common/utils';
import { KeycloakUser } from './keycloakuser.dto';

@Injectable({
  providedIn: 'root',
})
export class AuthorizationService {
  constructor(private authService: AuthService) {}

  public async checkRoles(roles: string[]): Promise<boolean> {
    if (this.authService.isLoggedIn()) {
      const user = await this.authService.getUser();
      const userRoles = (user?.profile as any).realm_access['roles'] || [];
      const permission = roles.some((role) => userRoles.includes(role));
      return permission;
    }
    return true;
  }

  public getUserDetails(): KeycloakUser {
    if (this.authService.isLoggedIn()) {
      const user = this.authService.getProfile();
      return {
        id: user.sub,
        lastName: user.family_name,
        firstName: user.name,
      } as KeycloakUser;
    }
    return {} as KeycloakUser;
  }
}
