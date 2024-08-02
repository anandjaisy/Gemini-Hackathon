import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TableComponent } from '../common/table/table.component';

@Component({
  selector: 'app-assessment',
  standalone: true,
  imports: [RouterOutlet, TableComponent],
  templateUrl: './assessment.component.html',
  styleUrl: './assessment.component.scss',
})
export class AssessmentComponent {}
