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
  studentName: string;
  questionId: string;
  suggestion: string;
  percentageMatched: string;
  grade: string;
}

export const initialState: StudentAssessmentState = {
  baseQuestion: '',
  baseAnswer: '',
  answer: '',
  assessmentId: '',
  assessmentName: '',
  studentId: '',
  studentName: '',
  questionId: '',
  suggestion: '',
  percentageMatched: '',
  grade: '',
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
    (state, { answer, studentId, studentName }) => ({
      ...state,
      answer: answer,
      studentId: studentId,
      studentName: studentName,
    })
  ),
  on(
    StudentAssessmentActions.updateAssessmentName,
    (state, { assessmentName }) => ({
      ...state,
      assessmentName: assessmentName,
    })
  ),
  on(
    StudentAssessmentActions.updateAssessmentScoreSuggestionGradePercentage,
    (state, { suggestion, percentageMatched, grade, answer }) => ({
      ...state,
      answer: answer,
      suggestion: suggestion,
      percentageMatched: percentageMatched,
      grade: grade,
    })
  )
);
