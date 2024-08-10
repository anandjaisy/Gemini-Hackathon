import {
  ApplicationConfig,
  importProvidersFrom,
  provideExperimentalZonelessChangeDetection,
  isDevMode,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { environment } from '../environments/environment';
import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideAnimations } from '@angular/platform-browser/animations';
import { FalconTailwindModule } from '@falcon-ng/tailwind';
import { FalconCoreModule } from '@falcon-ng/core';
import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';
import { AuthInterceptorService } from './common/auth-interceptor.service';
import { provideStore, StoreModule } from '@ngrx/store';
import { provideStoreDevtools } from '@ngrx/store-devtools';
import { provideEffects } from '@ngrx/effects';
import {
  studentAssessmentFeatureKey,
  studentAssessmentReducer,
} from './assessment/student-assessment/store/student-assessment.reducer';

export const appConfig: ApplicationConfig = {
  providers: [
    provideExperimentalZonelessChangeDetection(),
    provideRouter(routes),
    provideAnimations(),
    provideAnimationsAsync(),
    importProvidersFrom(FalconCoreModule),
    importProvidersFrom(FalconTailwindModule.forRoot(environment)),
    provideHttpClient(),
    provideAnimationsAsync(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
    provideStore({
      [studentAssessmentFeatureKey]: studentAssessmentReducer,
    }),
    provideStoreDevtools({ maxAge: 25, logOnly: !isDevMode() }),
    provideEffects(),
  ],
};
