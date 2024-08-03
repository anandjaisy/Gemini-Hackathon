import { CourseDto } from '../course/courseDto';

export interface AssessmentDto {
  id: string;
  name: string;
  description: string;
  course: CourseDto;
  createdDate: string;
  dueDate: string;
}
