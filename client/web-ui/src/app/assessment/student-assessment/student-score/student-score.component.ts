import { Component } from '@angular/core';
import { FalconCoreModule } from '@falcon-ng/core';

@Component({
  selector: 'app-student-score',
  standalone: true,
  imports: [FalconCoreModule],
  templateUrl: './student-score.component.html',
  styleUrl: './student-score.component.scss',
})
export class StudentScoreComponent {}
