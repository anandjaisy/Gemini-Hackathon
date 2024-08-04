import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FalconCoreModule } from '@falcon-ng/core';
import { QuestionService } from '../question.service';
import { of, Observable } from 'rxjs';
import { QuestionDto } from '../QuestionDto';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-question-details',
  standalone: true,
  imports: [FalconCoreModule, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './question-details.component.html',
  styleUrl: './question-details.component.scss',
})
export class QuestionDetailsComponent implements OnInit {
  private id: string = '';
  question$: Observable<QuestionDto> = of();
  constructor(
    private questionService: QuestionService,
    private activatedRoute: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.loadQuestion();
  }

  private loadQuestion(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      this.question$ = this.questionService.get(this.id);
    });
  }
}
