import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MensajeConfiguracionComponent } from './mensaje-configuracion.component';

describe('MensajeConfiguracionComponent', () => {
  let component: MensajeConfiguracionComponent;
  let fixture: ComponentFixture<MensajeConfiguracionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MensajeConfiguracionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MensajeConfiguracionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
