import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-assessment-details',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './assessment-details.component.html',
  styleUrl: './assessment-details.component.scss',
})
export class AssessmentDetailsComponent {}
