import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { FalconCoreModule } from '@falcon-ng/core';

@Component({
  selector: 'app-no-content',
  standalone: true,
  imports: [MatIconModule, FalconCoreModule],
  templateUrl: './no-content.component.html',
  styleUrl: './no-content.component.scss',
})
export class NoContentComponent {}
