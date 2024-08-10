import { createFeatureSelector, createSelector } from '@ngrx/store';
import {
  studentAssessmentFeatureKey,
  StudentAssessmentState,
} from './student-assessment.reducer';

const getStudentAssessmentState = createFeatureSelector<StudentAssessmentState>(
  studentAssessmentFeatureKey
);

export const selectStudentAssessmentState = createSelector(
  getStudentAssessmentState,
  (state: StudentAssessmentState) => state
);
