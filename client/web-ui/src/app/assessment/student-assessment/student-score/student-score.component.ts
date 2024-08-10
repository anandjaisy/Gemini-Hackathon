import { Component, OnInit } from '@angular/core';
import { FalconCoreModule } from '@falcon-ng/core';
import { Store } from '@ngrx/store';
import { StudentAssessmentState } from '../store/student-assessment.reducer';
import { Observable, of } from 'rxjs';
import { selectStudentAssessmentState } from '../store/student-assessment.selectors';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-student-score',
  standalone: true,
  imports: [FalconCoreModule, CommonModule, MatButtonModule, MatIconModule],
  templateUrl: './student-score.component.html',
  styleUrl: './student-score.component.scss',
})
export class StudentScoreComponent implements OnInit {
  studentAssessment$: Observable<StudentAssessmentState> = of();
  constructor(private store: Store) {}
  ngOnInit(): void {
    this.studentAssessment$ = this.store.select(selectStudentAssessmentState);
  }
}
