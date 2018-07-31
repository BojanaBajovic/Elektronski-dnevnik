import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';

import { Router } from '@angular/router';
import { UcenikEntity } from '../../Entities/ucenik-enitity';
import { UcenikService } from '../../services/ucenik.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-ucenik-list',
  templateUrl: './ucenik-list.component.html',
  styleUrls: ['./ucenik-list.component.css']
})

export class UcenikListComponent implements OnInit {

  ucenici: UcenikEntity[];

  constructor(private ucenikServices: UcenikService,
    private router: Router,
    private location: Location) { }

  ngOnInit() {
    this.getAllUcenik();
  }

  getAllUcenik(): void {
    this.ucenikServices.getAllUcenik()
    .subscribe(ucenici => this.ucenici = ucenici);
  }

  delete(id: number) {
    this.ucenikServices.deleteUcenik(id)
    .subscribe(
    _ => this.getAllUcenik()),
    this.router.navigate(['/ucenik']);
  }

}
