import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import {
  Appearance,
  Button,
  DatePicker,
  FalconCoreModule,
  IOptions,
  Select,
  Textarea,
  Textbox,
} from '@falcon-ng/core';
import { BaseFormComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { AssessmentService } from '../assessment.service';
import { Observable, of } from 'rxjs';
import { CourseService } from '../../course/course.service';
import { CourseDto } from '../../course/courseDto';
import { transformToKeyValuePair } from '../../common/utils';

@Component({
  selector: 'app-assessment-upsert',
  standalone: true,
  imports: [FalconCoreModule, ReactiveFormsModule, FalconTailwindModule],
  templateUrl: './assessment-upsert.component.html',
  styleUrl: './assessment-upsert.component.scss',
})
export class AssessmentUpsertComponent
  extends BaseFormComponent<string>
  implements OnInit
{
  private isNew: boolean = false;
  private id: string | undefined = undefined;
  constructor(
    private assessmentService: AssessmentService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private courseService: CourseService,
    private cdr: ChangeDetectorRef
  ) {
    super();
    this.defineForm();
  }
  ngOnInit(): void {
    this.formGroup = this.createControls();
    this.loadAssessment();
    this.loadCourse();
  }

  protected defineForm(): void {
    this.controlsConfig = {
      class: 'flex flex-col m-5',
      baseControls: [
        new Textbox({
          formControlName: 'name',
          label: 'Name',
          value: '',
          validations: [
            {
              name: 'required',
              validator: Validators.required,
              message: 'Required Field',
            },
          ],
        }),
        new Select({
          formControlName: 'courseId',
          label: 'Course',
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
        new DatePicker({
          formControlName: 'dueDate',
          label: 'Due date',
          class: 'w-full',
          validations: [
            {
              name: 'required',
              validator: Validators.required,
              message: 'Required Field',
            },
          ],
        }),
        new Textarea({
          formControlName: 'description',
          label: 'Description',
          value: '',
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

  private loadAssessment(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      if (this.id != 'new') {
        this.isNew = false;
        this.assessmentService
          .get(this.id as string)
          .subscribe((item) => this.patchValue(item));
      } else {
        this.isNew = true;
      }
    });
  }

  private loadCourse(): void {
    this.courseService.find().subscribe((courses: CourseDto[]) => {
      const options = transformToKeyValuePair(courses);
      this.controlsConfig.baseControls[1].options = options;
      this.cdr.detectChanges();
    });
  }

  protected submitDataSource(model: any): Observable<string> {
    if (this.isNew) {
      this.assessmentService
        .post(model)
        .subscribe(() => this.router.navigate(['./assessment']));
    } else {
      this.assessmentService
        .put(this.id as string, model)
        .subscribe(() => this.router.navigate(['./assessment']));
    }
    return of(model);
  }
}
