import { QuestionDto, UserDto } from '../question/QuestionDto';

export interface StudentAssessmentDto {
  id: string;
  studentId: string;
  questionId: string;
  answer: string;
}

export interface StudentAssessmentResponseDto {
  user: UserDto;
  question: QuestionDto;
}
