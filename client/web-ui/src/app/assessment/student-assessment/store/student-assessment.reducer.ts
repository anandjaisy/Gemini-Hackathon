import { createReducer, on } from '@ngrx/store';
import { StudentAssessmentActions } from './student-assessment.actions';

export const studentAssessmentFeatureKey = 'studentAssessment';

export interface StudentAssessmentState {
  baseQuestion: string;
  baseAnswer: string;
  answer: string;
  assessmentId: string;
  assessmentName: string;
  studentId: string;
  questionId: string;
}

export const initialState: StudentAssessmentState = {
  baseQuestion: '',
  baseAnswer: '',
  answer: '',
  assessmentId: '',
  assessmentName: '',
  studentId: '',
  questionId: '',
};

export const studentAssessmentReducer = createReducer(
  initialState,
  on(
    StudentAssessmentActions.updateBaseQuestionAnswer,
    (state, { baseQuestion, baseAnswer, questionId, assessmentId }) => ({
      ...state,
      baseAnswer: baseAnswer,
      baseQuestion: baseQuestion,
      questionId: questionId,
      assessmentId: assessmentId,
    })
  ),
  on(
    StudentAssessmentActions.updateAnswerStudentId,
    (state, { answer, studentId }) => ({
      ...state,
      answer: answer,
      studentId: studentId,
    })
  ),
  on(
    StudentAssessmentActions.updateAssessmentName,
    (state, { assessmentName }) => ({
      ...state,
      assessmentName: assessmentName,
    })
  )
);
