import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAssessmentComponent } from './student-assessment.component';

describe('StudentAssessmentComponent', () => {
  let component: StudentAssessmentComponent;
  let fixture: ComponentFixture<StudentAssessmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentAssessmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentAssessmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
