import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OcenaListComponent } from './ocena-list.component';

describe('OcenaListComponent', () => {
  let component: OcenaListComponent;
  let fixture: ComponentFixture<OcenaListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OcenaListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OcenaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
