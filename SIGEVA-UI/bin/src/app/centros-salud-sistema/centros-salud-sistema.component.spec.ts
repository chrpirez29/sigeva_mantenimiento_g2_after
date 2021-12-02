import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CentrosSaludSistemaComponent } from './centros-salud-sistema.component';

describe('CentrosSaludSistemaComponent', () => {
  let component: CentrosSaludSistemaComponent;
  let fixture: ComponentFixture<CentrosSaludSistemaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CentrosSaludSistemaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentrosSaludSistemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
