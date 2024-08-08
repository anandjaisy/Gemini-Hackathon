import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionDetailsRouterComponent } from './question-details-router.component';

describe('QuestionDetailsRouterComponent', () => {
  let component: QuestionDetailsRouterComponent;
  let fixture: ComponentFixture<QuestionDetailsRouterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuestionDetailsRouterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuestionDetailsRouterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
