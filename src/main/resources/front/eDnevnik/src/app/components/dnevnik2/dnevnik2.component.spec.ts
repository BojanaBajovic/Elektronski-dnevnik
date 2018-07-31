import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Dnevnik2Component } from './dnevnik2.component';

describe('Dnevnik2Component', () => {
  let component: Dnevnik2Component;
  let fixture: ComponentFixture<Dnevnik2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Dnevnik2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Dnevnik2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
