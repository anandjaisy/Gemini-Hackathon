import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {AuthCallbackComponent} from "./auth-callback/auth-callback.component";
import {CourseComponent} from "./course/course.component";
import {EnrolmentComponent} from "./course/enrolment/enrolment.component";

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'auth-callback', component: AuthCallbackComponent },
  { path: 'course', component: CourseComponent },
  { path: 'enrolment', component: EnrolmentComponent },
];
