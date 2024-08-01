import { Component, OnInit } from '@angular/core';
import {
  FalconCoreModule,
  Appearance,
  Textbox,
  Button,
  Textarea,
} from '@falcon-ng/core';
import { BaseFormComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { Validators, ReactiveFormsModule } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { CourseService } from '../course.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-course-upsert',
  standalone: true,
  imports: [FalconCoreModule, ReactiveFormsModule, FalconTailwindModule],
  templateUrl: './course-upsert.component.html',
  styleUrl: './course-upsert.component.scss',
})
export class CourseUpsertComponent
  extends BaseFormComponent<string>
  implements OnInit
{
  private isNew: boolean = false;
  private id: string | undefined = undefined;
  constructor(
    private courseUpsertService: CourseService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    super();
    this.defineForm();
  }

  ngOnInit(): void {
    this.formGroup = this.createControls();
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

  private loadCourse(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      if (this.id != 'new') {
        this.isNew = false;
        this.courseUpsertService
          .get(this.id as string)
          .subscribe((item) => this.patchValue(item));
      } else {
        this.isNew = true;
      }
    });
  }

  protected submitDataSource(model: any): Observable<string> {
    if (this.isNew) {
      this.courseUpsertService
        .post(model)
        .subscribe(() => this.router.navigate(['./course']));
    } else {
      this.courseUpsertService
        .put(this.id as string, model)
        .subscribe(() => this.router.navigate(['./course']));
    }
    return of(model);
  }
}
