import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseUpsertComponent } from './course-upsert.component';

describe('CourseUpsertComponent', () => {
  let component: CourseUpsertComponent;
  let fixture: ComponentFixture<CourseUpsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseUpsertComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseUpsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
