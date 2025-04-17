import { Component, Input, input } from '@angular/core';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ciudad-view',
  imports: [CommonModule],
  templateUrl: './ciudad-view.component.html',
  styleUrl: './ciudad-view.component.css'
})
export class CiudadViewComponent {
  @Input()
  ciudad: CiudadDto | undefined;
}
