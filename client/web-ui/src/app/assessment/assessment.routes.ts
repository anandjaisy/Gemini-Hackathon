import { Routes } from '@angular/router';
import { AssessmentListComponent } from './assessment-list/assessment-list.component';
import { QuestionComponent } from './question/question.component';
import { AssessmentUpsertComponent } from './assessment-upsert/assessment-upsert.component';

export const assessmentRoutes: Routes = [
  {
    path: '',
    component: AssessmentListComponent,
  },
  {
    path: 'question',
    component: QuestionComponent,
  },
  {
    path: ':id',
    component: AssessmentUpsertComponent,
  },
];
