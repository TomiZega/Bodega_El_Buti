import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PantallaGenerarRankingVideosComponent } from './pantalla-generar-ranking-videos.component';

describe('PantallaGenerarRankingVideosComponent', () => {
  let component: PantallaGenerarRankingVideosComponent;
  let fixture: ComponentFixture<PantallaGenerarRankingVideosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PantallaGenerarRankingVideosComponent]
    });
    fixture = TestBed.createComponent(PantallaGenerarRankingVideosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
