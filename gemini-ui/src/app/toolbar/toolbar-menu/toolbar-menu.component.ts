import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
// @ts-ignore
import { IOptions } from '@falcon-ng/tailwind/lib/model/interface';

@Component({
  selector: 'app-toolbar-menu',
  standalone: true,
  imports: [RouterModule, MatListModule, MatIconModule],
  templateUrl: './toolbar-menu.component.html',
  styleUrl: './toolbar-menu.component.scss'
})
export class ToolbarMenuComponent {
  fillerNav: IOptions[] = [
    { key: 'course', value: 'Course', icon: 'view_list' },
    { key: 'enrolment', value: 'Enrolment', icon: 'task' },
  ];
}
