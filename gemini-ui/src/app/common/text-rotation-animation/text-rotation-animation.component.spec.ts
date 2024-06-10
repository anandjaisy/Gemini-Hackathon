import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextRotationAnimationComponent } from './text-rotation-animation.component';

describe('TextRotationAnimationComponent', () => {
  let component: TextRotationAnimationComponent;
  let fixture: ComponentFixture<TextRotationAnimationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TextRotationAnimationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TextRotationAnimationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
