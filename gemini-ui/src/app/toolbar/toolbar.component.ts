import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Appearance, Button, InputTypes } from '@falcon-ng/core';
import { AuthService } from '@falcon-ng/tailwind';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { FalconCoreModule } from '@falcon-ng/core';
import { FalconTailwindModule } from '@falcon-ng/tailwind';
@Component({
  selector: 'app-toolbar',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    FalconCoreModule,
    FalconTailwindModule,
  ],
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss',
})
export class ToolbarComponent {
  signInButtonConfig = new Button({
    label: 'Sign in',
    appearance: Appearance.Raised,
    type: InputTypes.Button,
    color: 'accent',
    formControlName: 'SignInBtn',
    class: 'font-thin',
  });

  signOutButtonConfig = new Button({
    label: 'Sign out',
    appearance: Appearance.Raised,
    color: 'primary',
    class: 'flex justify-center',
  });

  signInClick() {
    //    this.router.navigate(['/upload']);
    this.authService.isServiceReady().then(() => {
      this.authService.startAuthentication('/');
    });
  }

  signOutclick() {
    this.authService.isServiceReady().then(() => {
      this.authService.logout();
    });
  }
  @Input({ required: true }) sidenav: MatSidenav | undefined;
  constructor(public authService: AuthService, private router: Router) {}
}
