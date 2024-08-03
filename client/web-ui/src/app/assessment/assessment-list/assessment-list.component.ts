import { ChangeDetectorRef, Component } from '@angular/core';
import { FalconCoreModule, IDialogData } from '@falcon-ng/core';
import { DialogComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AssessmentService } from '../assessment.service';
import { AssessmentDto } from '../AssessmentDto';
import { TableComponent } from '../../common/table/table.component';
@Component({
  selector: 'app-assessment-list',
  standalone: true,
  imports: [FalconTailwindModule, FalconCoreModule, TableComponent],
  templateUrl: './assessment-list.component.html',
  styleUrl: './assessment-list.component.scss',
})
export class AssessmentListComponent {
  headers: string[] = ['Name', 'Course', 'Due Date', 'Action'];
  assessmentData: AssessmentDto[] = [];
  private iDialogData: IDialogData = {} as IDialogData;
  headerToKeyMap: { [key: string]: string } = {
    Name: 'name',
    Course: 'course.name',
    'Due Date': 'dueDate',
  };
  constructor(
    private assessmentService: AssessmentService,
    private router: Router,
    private dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) {}
  ngOnInit(): void {
    this.loadAssessment();
  }

  editEmitterAction(event: string): void {
    this.router.navigate([`./assessment/${event}`]);
  }
  deleteEmitterAction(event: string): void {
    this.iDialogData.title = 'Delete';
    this.iDialogData.bodyMessage = 'Are you sure you want to delete ?';
    this.iDialogData.cancelBtnText = 'No';
    this.iDialogData.mainbtnText = 'Yes';
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: this.iDialogData,
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.assessmentService
        .delete(event)
        .subscribe(() => this.loadAssessment());
    });
  }

  private loadAssessment(): void {
    this.assessmentService.find().subscribe((item) => {
      this.assessmentData = item;
      this.cdr.detectChanges();
    });
  }
}
