import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FalconCoreModule, IDialogData } from '@falcon-ng/core';
import { DialogComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CourseDto } from '../courseDto';
import { CourseService } from '../course.service';
import { TableComponent } from '../../common/table/table.component';
import { AuthorizationService } from '../../auth-callback/authorization.service';
import { Permission, Role } from '../../common/utils';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [FalconTailwindModule, FalconCoreModule, TableComponent],
  templateUrl: './course-list.component.html',
  styleUrl: './course-list.component.scss',
})
export class CourseListComponent implements OnInit {
  permission: Promise<Permission> = Promise.resolve({} as Permission);
  headers: string[] = ['Name', 'Description'];
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
    private cdr: ChangeDetectorRef,
    private authorizationService: AuthorizationService
  ) {}
  ngOnInit(): void {
    this.loadCourses();
    this.permission = this.permissionCheck();
    this.permission.then((permission) => {
      if (permission) this.headers.push('Action');
    });
  }

  private async permissionCheck(): Promise<Permission> {
    const adminTeacher = await this.authorizationService.checkRoles([
      Role.ADMIN,
      Role.TEACHER,
      Role.STUDENT,
    ]);
    const student = await this.authorizationService.checkRoles([
      Role.ADMIN,
      Role.TEACHER,
      Role.STUDENT,
    ]);
    return {
      viewPermission: adminTeacher || student,
      editPermission: adminTeacher,
      deletePermission: adminTeacher,
    } as Permission;
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
