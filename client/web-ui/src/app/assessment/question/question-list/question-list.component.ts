import { ChangeDetectorRef, Component, EventEmitter } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ActivatedRoute, RouterModule } from '@angular/router';
import {
  BaseControl,
  FalconCoreModule,
  IComponentEvent,
  Select,
} from '@falcon-ng/core';
import { AssessmentService } from '../../assessment.service';
import { AssessmentDto } from '../../AssessmentDto';
import { Role, transformToKeyValuePair } from '../../../common/utils';
import { QuestionService } from '../question.service';
import { combineLatest } from 'rxjs';
import { QuestionDto } from '../QuestionDto';
import { CommonModule } from '@angular/common';
import { AuthorizationService } from '../../../auth-callback/authorization.service';
import { NoContentComponent } from '../../../common/no-content/no-content.component';
import { Store } from '@ngrx/store';
import { StudentAssessmentActions } from '../../student-assessment/store/student-assessment.actions';

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
    NoContentComponent,
  ],
  templateUrl: './question-list.component.html',
  styleUrl: './question-list.component.scss',
})
export class QuestionListComponent {
  permission: Promise<boolean> = Promise.resolve(false);
  questionList: QuestionDto[] = [];
  changeEvent: IComponentEvent<string> = { change: new EventEmitter<string>() };
  select: BaseControl<string> = new Select({
    formControlName: 'assessment',
    label: 'Select assessment',
    options: [],
    event: this.changeEvent,
  });
  form: FormGroup;
  courseName: string | undefined = undefined;
  private id: string | undefined = undefined;
  constructor(
    private assessmentService: AssessmentService,
    private questionService: QuestionService,
    private route: ActivatedRoute,
    private authorizationService: AuthorizationService,
    private store: Store,
    private cdr: ChangeDetectorRef
  ) {
    this.form = new FormGroup({});
  }
  ngOnInit(): void {
    this.permission = this.permissionCheck();
    this.id = this.route.parent?.parent?.snapshot.params['id'];
    this.select.value = this.id;
    this.changeEvent.change?.subscribe((event: any) =>
      this.loadQuestionForAssessment(event.value)
    );
    this.loadAssessmentQuestion();
  }

  private async permissionCheck(): Promise<boolean> {
    return await this.authorizationService.checkRoles([
      Role.ADMIN,
      Role.TEACHER,
    ]);
  }

  private loadQuestionForAssessment(id: string): void {
    this.questionService.find(id).subscribe((questions) => {
      this.questionList = questions;
      this.cdr.detectChanges();
    });
  }

  private loadAssessmentQuestion(): void {
    combineLatest([
      this.assessmentService.find(),
      this.questionService.find(this.id),
    ]).subscribe(([assessments, questions]) => {
      this.select.options = transformToKeyValuePair(assessments);
      this.questionList = questions;
      this.courseName = (assessments as AssessmentDto[]).find(
        (x) => x.id == this.id
      )?.course.name;
      this.cdr.detectChanges();
      const assessmentName = assessments.find((x: any) => x.id == this.id).name;
      this.store.dispatch(
        StudentAssessmentActions.updateAssessmentName({
          assessmentName: assessmentName,
        })
      );
    });
  }
}
