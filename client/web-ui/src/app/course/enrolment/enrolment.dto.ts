import { KeycloakUser } from '../../auth-callback/keycloakuser.dto';
import { CourseDto } from '../courseDto';

export interface EnrolmentDto {
  id: string;
  courseId: CourseDto;
  professorUser: KeycloakUser;
  studentUser: KeycloakUser;
}
