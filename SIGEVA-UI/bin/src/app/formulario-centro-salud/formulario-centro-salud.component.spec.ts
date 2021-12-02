import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioCentroSaludComponent } from './formulario-centro-salud.component';

describe('FormularioCentroSaludComponent', () => {
  let component: FormularioCentroSaludComponent;
  let fixture: ComponentFixture<FormularioCentroSaludComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormularioCentroSaludComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormularioCentroSaludComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
