import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { EKorisnikRole } from '../../Entities/EKorisnikRole';
import { NastavnikEntity } from '../../Entities/NastavnikEntity';
import { NastavnikService } from '../../services/nastavnik.service';

@Component({
  selector: 'app-nastavnik-form',
  templateUrl: './nastavnik-form.component.html',
  styleUrls: ['./nastavnik-form.component.css']
})
export class NastavnikFormComponent implements OnInit {
  nastavnik: NastavnikEntity;

  constructor(private router: Router,
    private location: Location,
    private nastavnikService: NastavnikService) {
    this.nastavnik = new NastavnikEntity();
  }

  ngOnInit() {
  }

  addNewNastavnik(ime: string, prezime: string, username: string, password: string, repeatedPassword: string, email: string) {
      this.nastavnik.ime = ime;
      this.nastavnik.prezime = prezime;
      this.nastavnik.username = username;
      this.nastavnik.password = password;
      this.nastavnik.repeatedPassword = repeatedPassword;
      this.nastavnik.email = email;

      this.nastavnikService.addNewNastavnik(this.nastavnik)
        .subscribe((nastavnik: NastavnikEntity) => {
          alert('Nastavnik ' + this.nastavnik.username + ' je uspešno dodat!');
          this.router.navigate(['/nastavnik']);
        });
    }

    // goBack() {
    //   this.location.back();
    // }
}
