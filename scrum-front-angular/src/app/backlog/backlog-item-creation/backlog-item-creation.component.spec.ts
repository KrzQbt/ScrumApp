import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogItemCreationComponent } from './backlog-item-creation.component';

describe('BacklogItemCreationComponent', () => {
  let component: BacklogItemCreationComponent;
  let fixture: ComponentFixture<BacklogItemCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BacklogItemCreationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BacklogItemCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
