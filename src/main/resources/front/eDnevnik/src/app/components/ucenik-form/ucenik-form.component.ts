import { Component, OnInit, Input } from '@angular/core';
import { UcenikEntity } from '../../Entities/ucenik-enitity';
import { UcenikService } from '../../services/ucenik.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { EKorisnikRole } from '../../Entities/EKorisnikRole';


@Component({
  selector: 'app-ucenik-form',
  templateUrl: './ucenik-form.component.html',
  styleUrls: ['./ucenik-form.component.css']
})
export class UcenikFormComponent implements OnInit {
  ucenik: UcenikEntity;

  constructor(private router: Router,
    private location: Location,
    private ucenikService: UcenikService) {
    this.ucenik = new UcenikEntity();
  }

  ngOnInit() {
  }

  addNewUcenik(ime: string, prezime: string, username: string, password: string, repeatedPassword: string, email: string) {
    this.ucenik.ime = ime;
    this.ucenik.prezime = prezime;
    this.ucenik.username = username;
    this.ucenik.password = password;
    this.ucenik.repeatedPassword = repeatedPassword;

    this.ucenikService.addNewUcenik(this.ucenik)
      .subscribe((ucenik: UcenikEntity) => {
        alert('Ucenik ' + this.ucenik.username + ' je uspešno dodat!');
        this.router.navigate(['/ucenik']);
      });
  }

}
