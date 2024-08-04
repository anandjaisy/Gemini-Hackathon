import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FalconCoreModule, IDialogData } from '@falcon-ng/core';
import { QuestionService } from '../question.service';
import { of, Observable } from 'rxjs';
import { QuestionDto } from '../QuestionDto';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '@falcon-ng/tailwind';

@Component({
  selector: 'app-question-details',
  standalone: true,
  imports: [FalconCoreModule, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './question-details.component.html',
  styleUrl: './question-details.component.scss',
})
export class QuestionDetailsComponent implements OnInit {
  private iDialogData: IDialogData = {} as IDialogData;
  private id: string = '';
  question$: Observable<QuestionDto> = of();
  constructor(
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) {}
  ngOnInit(): void {
    this.loadQuestion();
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
}
