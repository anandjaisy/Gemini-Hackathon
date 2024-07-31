import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AuthCallbackComponent } from './auth-callback/auth-callback.component';
import { CourseComponent } from './course/course.component';
import { EnrolmentComponent } from './course/enrolment/enrolment.component';
import { CourseUpsertComponent } from './course/course-upsert/course-upsert.component';
import { AssessmentComponent } from './assessment/assessment.component';
import { AssessmentUpsertComponent } from './assessment/assessment-upsert/assessment-upsert.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'auth-callback', component: AuthCallbackComponent },
  { path: 'course', component: CourseComponent },
  { path: 'course/:id', component: CourseUpsertComponent },
  { path: 'enrolment', component: EnrolmentComponent },
  { path: 'assessment', component: AssessmentComponent },
  { path: 'assessment/:id', component: AssessmentUpsertComponent },
];
