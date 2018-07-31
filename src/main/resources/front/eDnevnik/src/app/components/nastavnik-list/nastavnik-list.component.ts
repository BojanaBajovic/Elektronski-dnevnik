import { Component, OnInit, Input } from '@angular/core';
import { NastavnikEntity } from '../../Entities/NastavnikEntity';
import { NastavnikService } from '../../services/nastavnik.service';

@Component({
  selector: 'app-nastavnik-list',
  templateUrl: './nastavnik-list.component.html',
  styleUrls: ['./nastavnik-list.component.css']
})
export class NastavnikListComponent implements OnInit {

  @Input() model: NastavnikEntity[];

  nastavnici: NastavnikEntity[];

  constructor(private nastavnikService: NastavnikService) { }

  ngOnInit() {
    this.getAllNastavnik();
  }

  getAllNastavnik(): void {
    this.nastavnikService.getAllNastavnik()
    .subscribe(nastavnici => this.nastavnici = nastavnici);
  }

}
