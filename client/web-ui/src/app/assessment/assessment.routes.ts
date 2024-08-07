import { Routes } from '@angular/router';
import { AssessmentListComponent } from './assessment-list/assessment-list.component';
import { AssessmentUpsertComponent } from './assessment-upsert/assessment-upsert.component';
import { AssessmentDetailsComponent } from './assessment-details/assessment-details.component';

export const assessmentRoutes: Routes = [
  {
    path: '',
    component: AssessmentListComponent,
  },
  {
    path: ':id',
    component: AssessmentDetailsComponent,
    children: [
      {
        path: '',
        component: AssessmentUpsertComponent,
      },
      {
        path: 'question',
        loadChildren: () =>
          import('./question/question.routes').then((x) => x.questionRoutes),
      },
    ],
  },
];
