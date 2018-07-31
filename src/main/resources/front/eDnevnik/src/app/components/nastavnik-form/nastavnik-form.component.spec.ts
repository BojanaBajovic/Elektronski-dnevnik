import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NastavnikFormComponent } from './nastavnik-form.component';

describe('NastavnikFormComponent', () => {
  let component: NastavnikFormComponent;
  let fixture: ComponentFixture<NastavnikFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NastavnikFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NastavnikFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
