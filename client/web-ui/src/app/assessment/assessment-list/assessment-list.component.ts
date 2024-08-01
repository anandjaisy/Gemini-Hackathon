import { Component } from '@angular/core';
import {
  FalconCoreModule,
  IDialogData,
  MatTable,
  MatTableConfig,
  TableAction,
} from '@falcon-ng/core';
import { DialogComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { IBreadCrumb } from '../../common/bread-crumb/bread-crumb.component';
import { AssessmentUpsertService } from '../assessment-upsert/assessment-upsert.service';
import { CourseDto } from '../../course/courseDto';
@Component({
  selector: 'app-assessment-list',
  standalone: true,
  imports: [FalconTailwindModule, FalconCoreModule],
  templateUrl: './assessment-list.component.html',
  styleUrl: './assessment-list.component.scss',
})
export class AssessmentListComponent {
  public displayedColumns = ['action'];
  matTableConfig: MatTableConfig = {};
  breadCrumbList: IBreadCrumb[] = [];
  columns: MatTable[] = [
    {
      columnDef: 'name',
      header: 'Name',
      cell: (element: any) => `${element.name}`,
    },
    {
      columnDef: 'course',
      header: 'Course',
      cell: (element: any) => `${element.course}`,
    },
    {
      columnDef: 'assessmentDueDate',
      header: 'Due date',
      cell: (element: any) => `${element.assessmentDueDate}`,
    },
  ];

  private courseData: CourseDto[] = [];
  private iDialogData: IDialogData = {} as IDialogData;

  constructor(
    private assessmentUpsertService: AssessmentUpsertService,
    private router: Router,
    private dialog: MatDialog
  ) {}
  ngOnInit(): void {
    this.matTableConfig.columns = this.columns;
    this.matTableConfig.filter = true;
    this.matTableConfig.paginationConfig = {
      pagination: true,
      pageSizeOptions: [10, 50, 100],
    };
    this.matTableConfig.action = {
      edit: true,
      delete: true,
      isMenu: false,
    };
    this.loadAssessment();
  }

  tableActionRowEvent(event: any) {
    switch (event.action) {
      case TableAction.Edit:
        this.router.navigate([`./assessment/${event.id}`]);
        break;
      case TableAction.Delete:
        this.iDialogData.title = 'Delete';
        this.iDialogData.bodyMessage = 'Are you sure you want to delete ?';
        this.iDialogData.cancelBtnText = 'No';
        this.iDialogData.mainbtnText = 'Yes';
        const dialogRef = this.dialog.open(DialogComponent, {
          width: '350px',
          data: this.iDialogData,
        });
        dialogRef.afterClosed().subscribe((result) => {
          this.assessmentUpsertService
            .delete(event.id)
            .subscribe(() => this.loadAssessment());
        });
        break;
    }
  }

  private loadAssessment(): void {
    this.assessmentUpsertService.find().subscribe((item) => {
      this.courseData = item;
      this.matTableConfig.dataSource = this.courseData;
    });
  }
}
