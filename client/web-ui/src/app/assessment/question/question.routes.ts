import { Routes } from '@angular/router';
import { QuestionDetailsComponent } from './question-details/question-details.component';
import { QuestionListComponent } from './question-list/question-list.component';

export const questionRoutes: Routes = [
  {
    path: '',
    component: QuestionListComponent,
  },
  {
    path: ':id',
    component: QuestionDetailsComponent,
  },
];
