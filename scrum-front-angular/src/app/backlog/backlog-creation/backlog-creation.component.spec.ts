import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogCreationComponent } from './backlog-creation.component';

describe('BacklogCreationComponent', () => {
  let component: BacklogCreationComponent;
  let fixture: ComponentFixture<BacklogCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BacklogCreationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BacklogCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
