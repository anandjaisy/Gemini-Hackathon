import { Component, OnInit } from '@angular/core';
import { FalconCoreModule } from '@falcon-ng/core';
import { Store } from '@ngrx/store';
import { StudentAssessmentState } from '../store/student-assessment.reducer';
import { Observable, of } from 'rxjs';
import { selectStudentAssessmentState } from '../store/student-assessment.selectors';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { StudentScoreService } from './student-score.service';
import { AssessmentScoreCriteria } from './assessment-score-criteria';
import { ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';
import { StudentAssessmentActions } from '../store/student-assessment.actions';
import { MatDividerModule } from '@angular/material/divider';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-student-score',
  standalone: true,
  imports: [
    FalconCoreModule,
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatChipsModule,
  ],
  templateUrl: './student-score.component.html',
  styleUrl: './student-score.component.scss',
})
export class StudentScoreComponent implements OnInit {
  studentAssessment$: Observable<StudentAssessmentState> = of();

  constructor(
    private store: Store,
    private studentScoreService: StudentScoreService,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.route.queryParams
      .pipe(filter((params: any) => params.studentId || params.questionId))
      .subscribe((params: any) => {
        this.loadDetails(params.studentId, params.questionId);
      });
    this.studentAssessment$ = this.store.select(selectStudentAssessmentState);
  }

  private loadDetails(studentId: string, questionId: string): void {
    const creteria = {
      studentId: studentId,
      questionId: questionId,
    } as AssessmentScoreCriteria;
    this.studentScoreService.find(creteria).subscribe((item) => {
      this.store.dispatch(
        StudentAssessmentActions.updateAssessmentScoreSuggestionGradePercentage(
          {
            answer: item[0].answer,
            suggestion: item[0].suggestion,
            percentageMatched: item[0].percentageMatched,
            grade: item[0].grade,
          }
        )
      );
    });
  }
}
