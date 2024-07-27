import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {
  Appearance,
  Button,
  FalconCoreModule,
  Textarea,
  Textbox,
} from '@falcon-ng/core';
import { BaseFormComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { AssessmentUpsertService } from './assessment-upsert.service';
import { Observable, of } from 'rxjs';

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
    private assessmentUpsertService: AssessmentUpsertService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    super();
    this.defineForm();
  }
  ngOnInit(): void {
    this.formGroup = this.createControls();
    this.loadAssessment();
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

  private loadAssessment(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      if (this.id != 'new') {
        this.isNew = false;
        this.assessmentUpsertService
          .get(this.id as string)
          .subscribe((item) => this.patchValue(item));
      } else {
        this.isNew = true;
      }
    });
  }

  protected submitDataSource(model: any): Observable<string> {
    if (this.isNew) {
      this.assessmentUpsertService
        .post(model)
        .subscribe(() => this.router.navigate(['./course']));
    } else {
      this.assessmentUpsertService
        .put(this.id as string, model)
        .subscribe(() => this.router.navigate(['./course']));
    }
    return of(model);
  }
}
