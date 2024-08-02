import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FalconCoreModule, IDialogData } from '@falcon-ng/core';
import { DialogComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CourseDto } from '../courseDto';
import { CourseService } from '../course.service';
import { TableComponent } from '../../common/table/table.component';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [FalconTailwindModule, FalconCoreModule, TableComponent],
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.scss',
})
export class CourseListComponent implements OnInit {
  headers: string[] = ['Name', 'Description', 'Action'];
  dataSource: CourseDto[] = [];
  private iDialogData: IDialogData = {} as IDialogData;
  headerToKeyMap: { [key: string]: string } = {
    Name: 'name',
    Description: 'description',
  };
  constructor(
    private courseUpsertService: CourseService,
    private router: Router,
    private dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) {}
  ngOnInit(): void {
    this.loadCourses();
  }

  editEmitterAction(event: string): void {
    this.router.navigate([`./course/${event}`]);
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
      this.courseUpsertService
        .delete(event)
        .subscribe(() => this.loadCourses());
    });
  }

  private loadCourses(): void {
    this.courseUpsertService.find().subscribe((item) => {
      this.dataSource = item;
      this.cdr.detectChanges();
    });
  }
}
