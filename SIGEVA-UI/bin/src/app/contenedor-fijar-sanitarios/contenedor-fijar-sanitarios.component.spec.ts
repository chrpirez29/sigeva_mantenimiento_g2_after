import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContenedorFijarSanitariosComponent } from './contenedor-fijar-sanitarios.component';

describe('FijarSanitariosComponent', () => {
  let component: ContenedorFijarSanitariosComponent;
  let fixture: ComponentFixture<ContenedorFijarSanitariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContenedorFijarSanitariosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContenedorFijarSanitariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
