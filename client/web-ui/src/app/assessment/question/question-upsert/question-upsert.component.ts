import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable, of } from 'rxjs';
import {
  Appearance,
  Button,
  FalconCoreModule,
  Select,
  Textarea,
} from '@falcon-ng/core';
import { BaseFormComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { AssessmentService } from '../../assessment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { transformToKeyValuePair } from '../../../common/utils';
import { QuestionService } from '../question.service';

@Component({
  selector: 'app-question-upsert',
  standalone: true,
  imports: [FalconCoreModule, ReactiveFormsModule, FalconTailwindModule],
  templateUrl: './question-upsert.component.html',
  styleUrl: './question-upsert.component.scss',
})
export class QuestionUpsertComponent
  extends BaseFormComponent<string>
  implements OnInit
{
  private isNew: boolean = false;
  private id: string | undefined = undefined;
  constructor(
    private assessmentService: AssessmentService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private questionService: QuestionService
  ) {
    super();
    this.defineForm();
  }
  ngOnInit(): void {
    this.formGroup = this.createControls();
    this.loadAssessment();
    this.loadQuestion();
  }

  protected defineForm(): void {
    this.controlsConfig = {
      class: 'flex flex-col m-5',
      baseControls: [
        new Textarea({
          formControlName: 'question',
          label: 'Type question',
          value: '',
          textAreaProperty: { rows: 8 },
          validations: [
            {
              name: 'required',
              validator: Validators.required,
              message: 'Required Field',
            },
          ],
        }),
        new Select({
          formControlName: 'assessmentId',
          label: 'Select assessment',
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
        new Textarea({
          formControlName: 'answer',
          label: 'Type answer',
          value: '',
          textAreaProperty: { rows: 8 },
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

  private loadQuestion(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      if (this.id != 'new') {
        this.isNew = false;
        this.questionService
          .get(this.id as string)
          .subscribe((item) => this.patchValue(item));
      } else {
        this.isNew = true;
      }
    });
  }

  private loadAssessment(): void {
    this.assessmentService.find().subscribe((item) => {
      const options = transformToKeyValuePair(item);
      this.controlsConfig.baseControls[1].options = options;
    });
  }

  protected submitDataSource(model: any): Observable<string> {
    if (this.isNew) {
      this.questionService
        .post(model)
        .subscribe(() => this.router.navigate(['./assessment/question']));
    } else {
      this.questionService
        .put(this.id as string, model)
        .subscribe(() => this.router.navigate(['./assessment/question']));
    }
    return of(model);
  }
}
