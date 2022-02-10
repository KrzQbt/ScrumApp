import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectSprintListingComponent } from './project-sprint-listing.component';

describe('ProjectSprintListingComponent', () => {
  let component: ProjectSprintListingComponent;
  let fixture: ComponentFixture<ProjectSprintListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectSprintListingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectSprintListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
