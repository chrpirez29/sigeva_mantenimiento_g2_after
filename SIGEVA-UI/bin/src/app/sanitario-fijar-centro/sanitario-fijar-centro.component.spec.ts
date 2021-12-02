import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SanitarioFijarCentroComponent } from './sanitario-fijar-centro.component';

describe('SanitarioFijarCentroComponent', () => {
  let component: SanitarioFijarCentroComponent;
  let fixture: ComponentFixture<SanitarioFijarCentroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SanitarioFijarCentroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SanitarioFijarCentroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
