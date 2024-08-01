import { Component, EventEmitter, OnInit } from '@angular/core';
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
import { CourseDto } from '../courseDto';
import { CourseUpsertService } from '../course-upsert/course-upsert.service';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [FalconTailwindModule, FalconCoreModule],
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.scss',
})
export class CourseListComponent implements OnInit {
  public displayedColumns = ['action'];
  matTableConfig: MatTableConfig = {};
  columns: MatTable[] = [
    {
      columnDef: 'name',
      header: 'Name',
      cell: (element: any) => `${element.name}`,
    },
    {
      columnDef: 'description',
      header: 'Description',
      cell: (element: any) => `${element.description}`,
    },
  ];

  private courseData: CourseDto[] = [];
  private iDialogData: IDialogData = {} as IDialogData;
  constructor(
    private courseUpsertService: CourseUpsertService,
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
    this.loadCourses();
  }
  tableActionRowEvent(event: any) {
    switch (event.action) {
      case TableAction.Edit:
        this.router.navigate([`./course/${event.id}`]);
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
          this.courseUpsertService
            .delete(event.id)
            .subscribe(() => this.loadCourses());
        });
        break;
    }
  }

  private loadCourses(): void {
    this.courseUpsertService.find().subscribe((item) => {
      this.courseData = item;
      this.matTableConfig.dataSource = this.courseData;
    });
  }
}
