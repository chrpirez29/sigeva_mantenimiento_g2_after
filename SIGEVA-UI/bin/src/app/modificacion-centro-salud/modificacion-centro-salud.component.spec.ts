import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificacionCentroSaludComponent } from './modificacion-centro-salud.component';

describe('ModificacionCentroSaludComponent', () => {
  let component: ModificacionCentroSaludComponent;
  let fixture: ComponentFixture<ModificacionCentroSaludComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModificacionCentroSaludComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModificacionCentroSaludComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
