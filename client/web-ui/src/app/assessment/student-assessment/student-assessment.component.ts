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
  ],
  templateUrl: './student-assessment.component.html',
  styleUrl: './student-assessment.component.scss',
})
export class StudentAssessmentComponent implements OnInit {
  studentAssessments: StudentAssessmentResponseDto[] = [];
  private assessmentId: string | undefined = undefined;
  private questionId: string | undefined = undefined;
  constructor(
    private studentAssessmentService: StudentAssessmentService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private authorizationService: AuthorizationService
  ) {}
  ngOnInit(): void {
    this.assessmentId = this.authorizationService.getUserDetails().id;
    this.questionId = this.route.parent?.snapshot.params['id'];
    this.loadData();
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
}
