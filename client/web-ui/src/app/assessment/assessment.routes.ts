import { Routes } from '@angular/router';
import { AssessmentListComponent } from './assessment-list/assessment-list.component';
import { AssessmentUpsertComponent } from './assessment-upsert/assessment-upsert.component';

export const assessmentRoutes: Routes = [
  {
    path: '',
    component: AssessmentListComponent,
  },
  {
    path: 'question',
    loadChildren: () =>
      import('./question/question.routes').then((x) => x.questionRoutes),
  },
  {
    path: ':id',
    component: AssessmentUpsertComponent,
  },
];
