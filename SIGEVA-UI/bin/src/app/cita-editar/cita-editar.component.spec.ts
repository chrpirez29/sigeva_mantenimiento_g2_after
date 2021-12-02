import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitaEditarComponent } from './cita-editar.component';

describe('CitaEditarComponent', () => {
  let component: CitaEditarComponent;
  let fixture: ComponentFixture<CitaEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitaEditarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CitaEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
