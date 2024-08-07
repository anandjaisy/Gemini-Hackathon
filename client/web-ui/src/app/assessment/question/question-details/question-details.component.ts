import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {
  BaseControl,
  FalconCoreModule,
  IDialogData,
  Textarea,
} from '@falcon-ng/core';
import { QuestionService } from '../question.service';
import { of, Observable } from 'rxjs';
import { QuestionDto } from '../QuestionDto';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '@falcon-ng/tailwind';
import { AuthorizationService } from '../../../auth-callback/authorization.service';
import { Role } from '../../../common/utils';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StudentAssessmentDto } from '../../student-assessment.dto';
import { StudentAssessmentService } from '../../student-assessment.service';

@Component({
  selector: 'app-question-details',
  standalone: true,
  imports: [
    FalconCoreModule,
    MatButtonModule,
    MatIconModule,
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './question-details.component.html',
  styleUrl: './question-details.component.scss',
})
export class QuestionDetailsComponent implements OnInit {
  studentPermission: Promise<boolean> = Promise.resolve(false);
  permission: Promise<boolean> = Promise.resolve(false);
  private iDialogData: IDialogData = {} as IDialogData;
  private id: string = '';
  private questionId: string | undefined = undefined;
  question$: Observable<QuestionDto> = of();
  studentAnswer: BaseControl<string> = new Textarea({
    formControlName: 'answer',
    label: 'Type your assessment',
    textAreaProperty: { rows: 10 },
    validations: [
      {
        name: 'required',
        validator: Validators.required,
        message: 'Required Field',
      },
    ],
  });
  form: FormGroup;
  constructor(
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private authorizationService: AuthorizationService,
    private studentAssessmentService: StudentAssessmentService
  ) {
    this.form = new FormGroup({});
  }
  ngOnInit(): void {
    this.questionId = this.route.snapshot.params['id'];
    this.permission = this.permissionCheck();
    this.studentPermission = this.studentPermissionCheck();
    this.loadQuestion();
  }

  private async studentPermissionCheck(): Promise<boolean> {
    return await this.authorizationService.checkRoles([Role.STUDENT]);
  }

  private async permissionCheck(): Promise<boolean> {
    return await this.authorizationService.checkRoles([
      Role.ADMIN,
      Role.TEACHER,
      Role.STUDENT,
    ]);
  }

  menuAction(action: number, id: string) {
    switch (action) {
      case 0:
        this.router.navigate([`./assessment/question/${id}`]);
        break;
      case 1:
        this.iDialogData.title = 'Delete';
        this.iDialogData.bodyMessage = 'Are you sure you want to delete ?';
        this.iDialogData.cancelBtnText = 'No';
        this.iDialogData.mainbtnText = 'Yes';
        const dialogRef = this.dialog.open(DialogComponent, {
          width: '350px',
          data: this.iDialogData,
        });
        dialogRef.afterClosed().subscribe((result) => {
          this.questionService
            .delete(id)
            .subscribe(() => this.router.navigate(['./assessment/question']));
        });
        break;
    }
  }

  private loadQuestion(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      this.question$ = this.questionService.get(this.id);
    });
  }

  onSubmit(form: FormGroup) {
    const studentAssessment = {
      studentId: this.authorizationService.getUserDetails().id,
      questionId: this.questionId,
      answer: form.value.answer,
    } as StudentAssessmentDto;
    this.studentAssessmentService
      .post(studentAssessment)
      .subscribe((status) => {});
  }
}
