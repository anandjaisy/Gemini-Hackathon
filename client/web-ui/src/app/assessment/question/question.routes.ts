import { Routes } from '@angular/router';
import { QuestionDetailsComponent } from './question-details/question-details.component';
import { QuestionListComponent } from './question-list/question-list.component';
import { QuestionUpsertComponent } from './question-upsert/question-upsert.component';

export const questionRoutes: Routes = [
  {
    path: '',
    component: QuestionListComponent,
  },
  {
    path: 'detail',
    children: [
      {
        path: ':id',
        component: QuestionDetailsComponent,
      },
    ],
  },
  {
    path: ':id',
    component: QuestionUpsertComponent,
  },
];
