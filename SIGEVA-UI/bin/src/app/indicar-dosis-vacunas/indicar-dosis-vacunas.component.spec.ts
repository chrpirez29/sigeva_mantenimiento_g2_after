import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndicarDosisVacunasComponent } from './indicar-dosis-vacunas.component';

describe('IndicarDosisVacunasComponent', () => {
  let component: IndicarDosisVacunasComponent;
  let fixture: ComponentFixture<IndicarDosisVacunasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndicarDosisVacunasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicarDosisVacunasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
