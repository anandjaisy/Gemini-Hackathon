import { Component } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { FalconCoreModule } from '@falcon-ng/core';
import { FalconTailwindModule } from '@falcon-ng/tailwind';
import { TableComponent } from '../../common/table/table.component';

@Component({
  selector: 'app-enrolment',
  standalone: true,
  imports: [FalconTailwindModule, FalconCoreModule, TableComponent],
  templateUrl: './enrolment.component.html',
  styleUrl: './enrolment.component.scss',
})
export class EnrolmentComponent {
  headers: string[] = ['Name', 'Title', 'Email', 'Role', 'Action'];
  dataSource: any[] = [
    {
      id: '1',
      name: 'Lindsay Walton',
      title: 'Front-end Developer',
      email: 'lindsay.waltongmail.com',
      role: 'Member',
    },
    {
      id: '2',
      name: 'Lindsay Walton',
      title: 'Front-end Developer',
      email: 'lindsay.waltongmail.com',
      role: 'Member',
    },
    {
      id: '3',
      name: 'Lindsay Walton',
      title: 'Front-end Developer',
      email: 'lindsay.waltongmail.com',
      role: 'Member',
    },
    {
      id: '4',
      name: 'Lindsay Walton',
      title: 'Front-end Developer',
      email: 'lindsay.waltongmail.com',
      role: 'Member',
    },
    {
      id: '5',
      name: 'Lindsay Walton',
      title: 'Front-end Developer',
      email: 'lindsay.waltongmail.com',
      role: 'Member',
    },
  ];
  headerToKeyMap: { [key: string]: string } = {
    Name: 'name',
    Title: 'title',
    Email: 'email',
    Role: 'role',
  };

  editEmitterAction(event: string): void {
    console.log(event);
  }
  deleteEmitterAction(event: string): void {
    console.log(event);
  }
}
