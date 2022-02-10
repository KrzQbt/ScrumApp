import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SprintTimePreferenceReportComponent } from './sprint-time-preference-report.component';

describe('SprintTimePreferenceReportComponent', () => {
  let component: SprintTimePreferenceReportComponent;
  let fixture: ComponentFixture<SprintTimePreferenceReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SprintTimePreferenceReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SprintTimePreferenceReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
