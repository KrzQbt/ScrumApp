import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeReportSiteComponent } from './time-report-site.component';

describe('TimeReportSiteComponent', () => {
  let component: TimeReportSiteComponent;
  let fixture: ComponentFixture<TimeReportSiteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimeReportSiteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TimeReportSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
