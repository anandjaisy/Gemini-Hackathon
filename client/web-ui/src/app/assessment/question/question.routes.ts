import { Routes } from '@angular/router';
import { QuestionDetailsComponent } from './question-details-router/question-details/question-details.component';
import { QuestionListComponent } from './question-list/question-list.component';
import { QuestionUpsertComponent } from './question-upsert/question-upsert.component';
import { StudentAssessmentComponent } from '../student-assessment/student-assessment.component';
import { QuestionDetailsRouterComponent } from './question-details-router/question-details-router.component';
import { StudentScoreComponent } from '../student-assessment/student-score/student-score.component';

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
        component: QuestionDetailsRouterComponent,
        children: [
          {
            path: '',
            component: QuestionDetailsComponent,
          },
          {
            path: 'students',
            component: StudentAssessmentComponent,
          },
          {
            path: 'score',
            component: StudentScoreComponent,
          },
        ],
      },
    ],
  },
  {
    path: ':id',
    component: QuestionUpsertComponent,
  },
];
