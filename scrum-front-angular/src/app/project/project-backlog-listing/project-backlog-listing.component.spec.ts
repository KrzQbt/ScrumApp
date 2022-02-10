import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectBacklogListingComponent } from './project-backlog-listing.component';

describe('ProjectBacklogListingComponent', () => {
  let component: ProjectBacklogListingComponent;
  let fixture: ComponentFixture<ProjectBacklogListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectBacklogListingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectBacklogListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
