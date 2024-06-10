import { Component } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { MatButtonModule } from '@angular/material/button';
import { TextRotationAnimationComponent } from '../common/text-rotation-animation/text-rotation-animation.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FooterComponent,MatButtonModule,TextRotationAnimationComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
