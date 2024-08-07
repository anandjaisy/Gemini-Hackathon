import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  FalconCoreModule,
  Appearance,
  Textbox,
  Button,
  Textarea,
  Select,
  IOptions,
} from '@falcon-ng/core';
import { BaseFormComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { Validators, ReactiveFormsModule } from '@angular/forms';
import { combineLatest, forkJoin, map, Observable, of, switchMap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from '../../course.service';
import { transformToKeyValuePair } from '../../../common/utils';
import { KeycloakAdminService } from '../../../auth-callback/keycloak-admin.service';
import { EnrolmentService } from '../enrolment.service';

@Component({
  selector: 'app-enrolment-upsert',
  standalone: true,
  imports: [FalconCoreModule, ReactiveFormsModule, FalconTailwindModule],
  templateUrl: './enrolment-upsert.component.html',
  styleUrl: './enrolment-upsert.component.scss',
})
export class EnrolmentUpsertComponent
  extends BaseFormComponent<string>
  implements OnInit
{
  private isNew: boolean = false;
  private id: string | undefined = undefined;
  constructor(
    private courseService: CourseService,
    private enrolmentService: EnrolmentService,
    private keycloakAdminService: KeycloakAdminService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    super();
    this.defineForm();
  }

  ngOnInit(): void {
    this.formGroup = this.createControls();
    this.loadCourse();
    this.loadInitialData();
  }

  protected defineForm(): void {
    this.controlsConfig = {
      class: 'flex flex-col m-5',
      baseControls: [
        new Select({
          formControlName: 'courseId',
          label: 'Select course',
          options: [],
          class: 'w-full',
          validations: [
            {
              name: 'required',
              validator: Validators.required,
              message: 'Required Field',
            },
          ],
        }),
        new Select({
          formControlName: 'professorId',
          label: 'Select professor',
          options: [],
          class: 'w-full',
          validations: [
            {
              name: 'required',
              validator: Validators.required,
              message: 'Required Field',
            },
          ],
        }),
        new Select({
          formControlName: 'studentId',
          label: 'Select student',
          options: [],
          class: 'w-full',
          validations: [
            {
              name: 'required',
              validator: Validators.required,
              message: 'Required Field',
            },
          ],
        }),
        new Button({
          label: 'Save',
          appearance: Appearance.Raised,
          color: 'primary',
          class: 'flex justify-center',
        }),
      ],
    };
  }

  private loadInitialData(): void {
    combineLatest([
      this.courseService.find(),
      this.keycloakAdminService.getUsers(),
    ])
      .pipe(
        switchMap(([courses, users]) => {
          this.controlsConfig.baseControls[0].options =
            transformToKeyValuePair(courses);
          return forkJoin(
            users.map((user) =>
              this.keycloakAdminService
                .getUserRoles(user.id)
                .pipe(map((roles) => ({ ...user, roles })))
            )
          );
        })
      )
      .subscribe((usersWithRoles) => {
        const optionUsers =
          this.transformKeyCloakUserToKeyValuePair(usersWithRoles);

        // Filter users based on roles
        const teachers = this.filterUsersByRole(usersWithRoles, 'teacher');
        const students = this.filterUsersByRole(usersWithRoles, 'student');

        // Transform users to key-value pairs
        const optionTeachers =
          this.transformKeyCloakUserToKeyValuePair(teachers);
        const optionStudents =
          this.transformKeyCloakUserToKeyValuePair(students);

        this.controlsConfig.baseControls[1].options = optionTeachers;
        this.controlsConfig.baseControls[2].options = optionStudents;
        this.cdr.detectChanges();
      });
  }

  private filterUsersByRole(users: any[], roleName: string): any[] {
    return users.filter((user) =>
      user.roles.some((role: any) => role.name === roleName)
    );
  }

  private transformKeyCloakUserToKeyValuePair<T>(array: any[]): IOptions[] {
    return array.map((item) => ({
      key: String(item['id']),
      value: String(item['firstName'] + item['lastName']),
    }));
  }

  private loadCourse(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      if (this.id != 'new') {
        this.isNew = false;
        this.enrolmentService.get(this.id as string).subscribe((item) => {
          this.patchValue(item);
        });
      } else {
        this.isNew = true;
      }
    });
  }

  protected submitDataSource(model: any): Observable<string> {
    if (this.isNew) {
      this.enrolmentService
        .post(model)
        .subscribe(() => this.router.navigate(['./course/enrolment']));
    } else {
      this.enrolmentService
        .put(this.id as string, model)
        .subscribe(() => this.router.navigate(['./course/enrolment']));
    }
    return of(model);
  }
}
