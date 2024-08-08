import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { FalconCoreModule, IDialogData } from '@falcon-ng/core';
import { DialogComponent, FalconTailwindModule } from '@falcon-ng/tailwind';
import { TableComponent } from '../../common/table/table.component';
import { EnrolmentService } from './enrolment.service';
import { EnrolmentDto } from './enrolment.dto';
import { Permission, Role } from '../../common/utils';
import { AuthorizationService } from '../../auth-callback/authorization.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-enrolment',
  standalone: true,
  imports: [FalconTailwindModule, FalconCoreModule, TableComponent],
  templateUrl: './enrolment.component.html',
  styleUrl: './enrolment.component.scss',
})
export class EnrolmentComponent implements OnInit {
  private iDialogData: IDialogData = {} as IDialogData;
  permission: Promise<Permission> = Promise.resolve({} as Permission);
  constructor(
    private enrolmentService: EnrolmentService,
    private dialog: MatDialog,
    private router: Router,
    private authorizationService: AuthorizationService,
    private cdr: ChangeDetectorRef
  ) {}

  headers: string[] = ['Course', 'Professor', 'Student'];
  dataSource: EnrolmentDto[] = [];
  headerToKeyMap: { [key: string]: string } = {
    Course: 'course.name',
    Professor: 'professorUser.firstName',
    Student: 'studentUser.firstName',
  };

  ngOnInit(): void {
    this.permission = this.permissionCheck();
    this.permission.then((permission) => {
      if (permission) this.headers.push('Action');
    });
    this.loadEnrolments();
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

  private loadEnrolments(): void {
    this.enrolmentService.find().subscribe((item) => {
      this.dataSource = item;
      this.cdr.detectChanges();
    });
  }

  editEmitterAction(event: string): void {
    console.log(event);
    this.router.navigate([`./course/enrolment/${event}`]);
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
      this.enrolmentService
        .delete(event)
        .subscribe(() => this.loadEnrolments());
    });
  }
}
