import { CourseDto } from '../course/courseDto';

export interface AssessmentDto {
  id: string;
  name: string;
  description: string;
  course: CourseDto;
  assessmentDueDate: string;
}
