import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AuthCallbackComponent } from './auth-callback/auth-callback.component';
import { CourseUpsertComponent } from './course/course-upsert/course-upsert.component';
import { AssessmentUpsertComponent } from './assessment/assessment-upsert/assessment-upsert.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'auth-callback', component: AuthCallbackComponent },
  {
    path: 'course',
    loadComponent: () =>
      import('./course/course.component').then((x) => x.CourseComponent),
    loadChildren: () =>
      import('./course/course.routes').then((x) => x.courseRoutes),
  },
  {
    path: 'assessment',
    loadComponent: () =>
      import('./assessment/assessment.component').then(
        (x) => x.AssessmentComponent
      ),
    loadChildren: () =>
      import('./assessment/assessment.routes').then((x) => x.assessmentRoutes),
  },
];
