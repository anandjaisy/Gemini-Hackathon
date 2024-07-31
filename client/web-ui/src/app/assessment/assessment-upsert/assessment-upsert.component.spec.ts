import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssessmentUpsertComponent } from './assessment-upsert.component';

describe('AssessmentUpsertComponent', () => {
  let component: AssessmentUpsertComponent;
  let fixture: ComponentFixture<AssessmentUpsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssessmentUpsertComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssessmentUpsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
