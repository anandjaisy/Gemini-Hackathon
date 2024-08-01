import { Routes } from '@angular/router';
import { CourseListComponent } from './course-list/course-list.component';
import { EnrolmentComponent } from './enrolment/enrolment.component';
import { CourseUpsertComponent } from './course-upsert/course-upsert.component';

export const courseRoutes: Routes = [
  {
    path: '',
    component: CourseListComponent,
  },
  {
    path: 'enrolment',
    component: EnrolmentComponent,
  },
  {
    path: ':id',
    component: CourseUpsertComponent,
  },
];
