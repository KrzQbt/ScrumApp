import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogOverviewComponent } from './backlog-overview.component';

describe('BacklogOverviewComponent', () => {
  let component: BacklogOverviewComponent;
  let fixture: ComponentFixture<BacklogOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BacklogOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BacklogOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
