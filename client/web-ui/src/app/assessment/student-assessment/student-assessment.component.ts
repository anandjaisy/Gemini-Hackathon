import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { FalconCoreModule } from '@falcon-ng/core';
import { StudentAssessmentService } from './student-assessment.service';
import { QuestionCriteria } from './questionCriteria';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-student-assessment',
  standalone: true,
  imports: [FalconCoreModule, MatDividerModule, MatButtonModule, MatIconModule],
  templateUrl: './student-assessment.component.html',
  styleUrl: './student-assessment.component.scss',
})
export class StudentAssessmentComponent implements OnInit {
  private assessmentId: string | undefined = undefined;
  private questionId: string | undefined = undefined;
  constructor(
    private studentAssessmentService: StudentAssessmentService,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.assessmentId =
      this.route.parent?.parent?.parent?.parent?.snapshot.params['id'];
    this.questionId = this.route.parent?.snapshot.params['id'];
    this.loadData();
  }

  private loadData(): void {
    this.studentAssessmentService
      .find({
        assessmentId: this.assessmentId,
        questionId: this.questionId,
      } as QuestionCriteria)
      .subscribe((item) => console.log(item));
  }
}
