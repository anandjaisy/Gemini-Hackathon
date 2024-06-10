import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router } from '@angular/router';
import {AppSettingService, AuthService, FalconCoreModule, LoggerService} from "@falcon-ng/tailwind";

@Component({
  selector: 'app-auth-callback',
  standalone: true,
  imports: [FalconCoreModule],
  templateUrl: './auth-callback.component.html',
  styleUrl: './auth-callback.component.scss'
})
export class AuthCallbackComponent implements OnInit {
  constructor(
    private appSettings: AppSettingService,
    private authService: AuthService,
    private router: Router,
    private logger: LoggerService
  ) {}

  async ngOnInit(): Promise<void> {
    this.appSettings.on(AppSettingService.APP_SETTINGS_LOADED, () => {
      this.completeAuthentication();
    });
    if (this.appSettings.initialized) {
      await this.completeAuthentication();
    }
  }

  async completeAuthentication() {
    try {
      await this.authService.userManager.signinCallback();
      this.router.navigate(['']);
    } catch (error) {
      this.logger.error(error);
    }
  }
}
