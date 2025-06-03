import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-caravana',
  imports: [],
  templateUrl: './caravana.component.html',
  styleUrl: './caravana.component.css'
})
export class CaravanaComponent implements OnInit {
  ngOnInit(): void {
    console.log('CaravanComponent cargado correctamente');
  }
}
