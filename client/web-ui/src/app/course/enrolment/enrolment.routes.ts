import { Routes } from '@angular/router';
import { EnrolmentComponent } from './enrolment.component';
import { CourseUpsertComponent } from '../course-upsert/course-upsert.component';
import { EnrolmentUpsertComponent } from './enrolment-upsert/enrolment-upsert.component';

export const enrolmentRoutes: Routes = [
  {
    path: '',
    component: EnrolmentComponent,
  },
  {
    path: ':id',
    component: EnrolmentUpsertComponent,
  },
];
