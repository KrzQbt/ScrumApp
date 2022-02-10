import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogDetailsComponent } from './backlog-details.component';

describe('BacklogDetailsComponent', () => {
  let component: BacklogDetailsComponent;
  let fixture: ComponentFixture<BacklogDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BacklogDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BacklogDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
