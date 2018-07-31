import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';

import { Router } from '@angular/router';
import { AdminEntity } from '../../entities/admin-entity';
import { AdminService } from '../../services/admin.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-listadmin',
  templateUrl: './listadmin.component.html',
  styleUrls: ['./listadmin.component.css']
})

export class ListadminComponent implements OnInit {

  admini: AdminEntity[];


  constructor(private router: Router, private adminService: AdminService, private location: Location) { }


    ngOnInit() {
      this.getAllAdmin();
    }

    getAllAdmin(): void {
      this.adminService.getAllAdmin()
      .subscribe(admini => this.admini = admini);
    }

  delete(id: number) {
    this.adminService.deleteAdmin(id)
    .subscribe(
    _ => this.getAllAdmin()),
    this.router.navigate(['/admin']);
  }

}
