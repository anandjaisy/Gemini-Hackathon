import {Component, OnInit} from '@angular/core';
import {Router } from '@angular/router';
import {AppSettingService, AuthService, FalconTailwindModule, LoggerService} from "@falcon-ng/tailwind";
import  {FalconCoreModule} from "@falcon-ng/core";

@Component({
  selector: 'app-auth-callback',
  standalone: true,
  imports: [FalconCoreModule, FalconTailwindModule],
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
