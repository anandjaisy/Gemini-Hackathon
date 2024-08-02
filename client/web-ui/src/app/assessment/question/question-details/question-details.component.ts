import { Component } from '@angular/core';
import { FalconCoreModule } from '@falcon-ng/core';

@Component({
  selector: 'app-question-details',
  standalone: true,
  imports: [FalconCoreModule],
  templateUrl: './question-details.component.html',
  styleUrl: './question-details.component.scss',
})
export class QuestionDetailsComponent {}
