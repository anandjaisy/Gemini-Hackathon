import { EnvironmentViewModel } from '@falcon-ng/tailwind';

class EnvironmentImpl implements EnvironmentViewModel {
  production = false;
  openID = {
    authority: 'http://localhost:5001/realms/FeteBird',
    client_id: 'fetebird_ai',
    redirect_uri: 'http://localhost:4200/auth-callback',
    silent_redirect_uri: 'http://localhost:4200/assets/silent-renew.html',
    post_logout_redirect_uri: 'http://localhost:4200',
    response_type: 'code',
    scope: 'openid profile email',
    automaticSilentRenew: true,
  };
  baseUrl = 'http://localhost:8080';
  snackBarEnable = true;
  keyCloakApiBaseUrl = 'http://localhost:5001/admin/realms/FeteBird/';
}

export const environment = new EnvironmentImpl();
