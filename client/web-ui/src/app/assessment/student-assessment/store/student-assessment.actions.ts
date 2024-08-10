import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { StudentAssessmentState } from './student-assessment.reducer';

export const StudentAssessmentActions = createActionGroup({
  source: 'StudentAssessment',
  events: {
    'Update BaseQuestionAnswer': props<{
      baseQuestion: string;
      baseAnswer: string;
      questionId: string;
      assessmentId: string;
    }>(),
    'Update AnswerStudentId': props<{
      answer: string;
      studentId: string;
    }>(),
    'Update AssessmentName': props<{
      assessmentName: string;
    }>(),
  },
});
