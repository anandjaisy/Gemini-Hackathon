import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrolmentUpsertComponent } from './enrolment-upsert.component';

describe('EnrolmentUpsertComponent', () => {
  let component: EnrolmentUpsertComponent;
  let fixture: ComponentFixture<EnrolmentUpsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnrolmentUpsertComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EnrolmentUpsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
