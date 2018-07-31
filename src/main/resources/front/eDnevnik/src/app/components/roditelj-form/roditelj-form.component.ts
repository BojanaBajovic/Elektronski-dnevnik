import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { EKorisnikRole } from '../../Entities/EKorisnikRole';
import { RoditeljEntity } from '../../Entities/RoditeljEntity';
import { RoditeljService } from '../../services/roditelj.service';


@Component({
  selector: 'app-roditelj-form',
  templateUrl: './roditelj-form.component.html',
  styleUrls: ['./roditelj-form.component.css']
})
export class RoditeljFormComponent implements OnInit {
  roditelj: RoditeljEntity;

  constructor(private router: Router,
    private location: Location,
    private roditeljService: RoditeljService) {
    this.roditelj = new RoditeljEntity();
  }

  ngOnInit() {
  }

  addNewRoditelj(ime: string, prezime: string, username: string, password: string, repeatedPassword: string, email: string) {
      this.roditelj.ime = ime;
      this.roditelj.prezime = prezime;
      this.roditelj.username = username;
      this.roditelj.password = password;
      this.roditelj.repeatedPassword = repeatedPassword;
      this.roditelj.email = email;

      this.roditeljService.addNewRoditelj(this.roditelj)
        .subscribe((roditelj: RoditeljEntity) => {
          alert('Roditelj ' + this.roditelj.username + ' je uspešno dodat!');
          this.router.navigate(['/roditelj']);
        });
    }

    // goBack() {
    //   this.location.back();
    // }
}
