import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-text-rotation-animation',
  standalone: true,
  imports: [],
  templateUrl: './text-rotation-animation.component.html',
  styleUrl: './text-rotation-animation.component.scss'
})
export class TextRotationAnimationComponent {
  // @ts-ignore
  @ViewChild('listItem') listItemElement: ElementRef;

  listItems = [
    'Cool Feature',
    'Another Feature',
    'Something Else', 
    'Why Not', 
    'Rotating', 
    'List', 
    'Items Here'
  ];
  currentIndex = 0;
  displayText = this.listItems[this.currentIndex];

  ngOnInit() {
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.listItems.length;
      this.displayText = this.listItems[this.currentIndex];
      this.listItemElement.nativeElement.classList.remove('fade-in', 'fade-from-bottom');
      this.listItemElement.nativeElement.classList.add('fade-out'); 
      setTimeout(() => {
        this.listItemElement.nativeElement.classList.remove('fade-out');
        this.listItemElement.nativeElement.classList.add('fade-in', 'fade-from-bottom'); 
        }, 1000);
      }, 2000); 
  }
}
