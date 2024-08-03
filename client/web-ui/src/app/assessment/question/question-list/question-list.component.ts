import { ChangeDetectorRef, Component, EventEmitter } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import {
  BaseControl,
  FalconCoreModule,
  IComponentEvent,
  Select,
} from '@falcon-ng/core';
import { AssessmentService } from '../../assessment.service';
import { AssessmentDto } from '../../AssessmentDto';
import { transformToKeyValuePair } from '../../../common/utils';
import { QuestionService } from '../question.service';
import { combineLatest } from 'rxjs';
import { QuestionDto } from '../QuestionDto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-question-list',
  standalone: true,
  imports: [
    FalconCoreModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatPaginatorModule,
    RouterModule,
    ReactiveFormsModule,
    CommonModule,
  ],
  templateUrl: './question-list.component.html',
  styleUrl: './question-list.component.scss',
})
export class QuestionListComponent {
  questionList: QuestionDto[] = [];
  changeEvent: IComponentEvent<string> = { change: new EventEmitter<string>() };
  select: BaseControl<string> = new Select({
    formControlName: 'assessment',
    label: 'Select assessment',
    options: [],
    event: this.changeEvent,
  });
  form: FormGroup;
  constructor(
    private assessmentService: AssessmentService,
    private questionService: QuestionService,
    private cdr: ChangeDetectorRef
  ) {
    this.form = new FormGroup({});
  }
  ngOnInit(): void {
    this.changeEvent.change?.subscribe((event: any) =>
      this.loadQuestionForAssessment(event.value)
    );
    this.loadAssessmentQuestion();
  }

  private loadQuestionForAssessment(id: string): void {
    this.questionService.find().subscribe((questions) => {
      this.questionList = questions;
      this.cdr.detectChanges();
    });
  }

  private loadAssessmentQuestion(): void {
    combineLatest([
      this.assessmentService.find(),
      this.questionService.find(),
    ]).subscribe(([assessments, questions]) => {
      this.select.options = transformToKeyValuePair(assessments);
      this.questionList = questions;
      this.cdr.detectChanges();
    });
  }
}
