import { AssessmentDto } from '../AssessmentDto';

export interface QuestionDto {
  id: string;
  assessment: AssessmentDto;
  question: string;
  answer: string;
  studentAnswer: string;
  createdDate: string;
  isSubmittedByStudent: boolean;
  isMarkedByTeacher: boolean;
}
