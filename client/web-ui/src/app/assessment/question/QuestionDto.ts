import { AssessmentDto } from '../AssessmentDto';

export interface QuestionDto {
  id: string;
  assessment: AssessmentDto;
  question: string;
  answer: string;
  createdDate: string;
  isSubmitedByStudent: boolean;
  isMarkedByTeacher: boolean;
}
