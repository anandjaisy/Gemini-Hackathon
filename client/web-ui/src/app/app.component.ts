import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ViewChild,
} from '@angular/core';
import {
  ActivatedRoute,
  NavigationEnd,
  Router,
  RouterOutlet,
} from '@angular/router';
import { FooterComponent } from './footer/footer.component';
import { ToolbarComponent } from './common/toolbar/toolbar.component';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { MediaMatcher } from '@angular/cdk/layout';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { CommonModule } from '@angular/common';
import { ToolbarMenuComponent } from './common/toolbar/toolbar-menu/toolbar-menu.component';
import { MatDividerModule } from '@angular/material/divider';
import { style } from '@angular/animations';
import { BreadcrumbService } from './common/bread-crumb/breadcrumb.service';
import {
  BreadCrumbComponent,
  IBreadCrumb,
} from './common/bread-crumb/bread-crumb.component';
import { AuthService } from '@falcon-ng/tailwind';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    FooterComponent,
    ToolbarComponent,
    CommonModule,
    RouterOutlet,
    ToolbarMenuComponent,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatDividerModule,
    FooterComponent,
    BreadCrumbComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  host: { '[style.--mat-sidenav-container-shape]': '"0px"' },
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent {
  title = 'Fetebird Ai';
  mobileQuery: MediaQueryList;

  fillerContent = Array.from(
    { length: 50 },
    () =>
      `Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
      labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
      laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
      voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
      cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`
  );

  private _mobileQueryListener: () => void;

  @ViewChild('sidenav') public sidenav: MatSidenav | undefined;
  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    public authService: AuthService
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.breadcrumbService.updateBreadcrumbs();
      }
    });
  }
}
