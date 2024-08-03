import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FalconCoreModule } from '@falcon-ng/core';

@Component({
  selector: 'app-question-details',
  standalone: true,
  imports: [FalconCoreModule, MatButtonModule, MatIconModule],
  templateUrl: './question-details.component.html',
  styleUrl: './question-details.component.scss',
})
export class QuestionDetailsComponent {}
