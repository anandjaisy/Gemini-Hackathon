import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

export interface PeriodicElement {
  name: string;
  position: number;
}
@Component({
  selector: 'app-course',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './course.component.html',
  styleUrl: './course.component.scss',
})
export class CourseComponent {}
