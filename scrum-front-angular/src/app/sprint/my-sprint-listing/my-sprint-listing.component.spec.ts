import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MySprintListingComponent } from './my-sprint-listing.component';

describe('MySprintListingComponent', () => {
  let component: MySprintListingComponent;
  let fixture: ComponentFixture<MySprintListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MySprintListingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MySprintListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
