import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { FalconCoreModule } from '@falcon-ng/core';
import { StudentAssessmentService } from './student-assessment.service';
import { QuestionCriteria } from './questionCriteria';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { StudentAssessmentResponseDto } from './student-assessment.dto';
import { CommonModule } from '@angular/common';
import { AuthorizationService } from '../../auth-callback/authorization.service';
import { NoContentComponent } from '../../common/no-content/no-content.component';
import { StudentAssessmentActions } from './store/student-assessment.actions';
import { Store } from '@ngrx/store';
import { ScoreRequest } from './score-request.dto';
import { StudentAssessmentState } from './store/student-assessment.reducer';
import { Observable, of } from 'rxjs';
import { selectStudentAssessmentState } from './store/student-assessment.selectors';

@Component({
  selector: 'app-student-assessment',
  standalone: true,
  imports: [
    FalconCoreModule,
    MatDividerModule,
    MatButtonModule,
    MatIconModule,
    CommonModule,
    RouterLink,
    NoContentComponent,
  ],
  templateUrl: './student-assessment.component.html',
  styleUrl: './student-assessment.component.scss',
})
export class StudentAssessmentComponent implements OnInit {
  studentAssessments: StudentAssessmentResponseDto[] = [];
  private assessmentId: string | undefined = undefined;
  private questionId: string | undefined = undefined;
  studentAssessment$: Observable<StudentAssessmentState> = of();
  showResult: boolean = false;
  constructor(
    private studentAssessmentService: StudentAssessmentService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private authorizationService: AuthorizationService,
    private store: Store
  ) {}
  ngOnInit(): void {
    this.assessmentId = this.authorizationService.getUserDetails().id;
    this.questionId = this.route.parent?.snapshot.params['id'];
    this.loadData();
    this.studentAssessment$ = this.store.select(selectStudentAssessmentState);
  }

  private loadData(): void {
    this.studentAssessmentService
      .find({
        studentId: this.assessmentId,
        questionId: this.questionId,
      } as QuestionCriteria)
      .subscribe((item) => {
        this.studentAssessments = item;
        this.cdr.detectChanges();
      });
  }

  evaluateWithAIClick(
    answer: string | undefined,
    studentId: string | undefined
  ): void {
    this.store.dispatch(
      StudentAssessmentActions.updateAnswerStudentId({
        answer: answer as string,
        studentId: studentId as string,
      })
    );
    this.evaluateScore();
  }

  private evaluateScore() {
    this.studentAssessment$.subscribe((state) => {
      this.showResult = false;
      const score: ScoreRequest = {
        baseQuestion: state.baseQuestion,
        baseAnswer: state.baseAnswer,
        answer: state.answer,
        studentId: state.studentId,
        questionId: state.questionId,
      };
      this.studentAssessmentService.postScore(score).subscribe(() => {
        this.showResult = true;
        this.cdr.detectChanges();
      });
    });
  }
}
