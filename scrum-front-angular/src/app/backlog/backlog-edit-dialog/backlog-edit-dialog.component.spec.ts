import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogEditDialogComponent } from './backlog-edit-dialog.component';

describe('BacklogEditDialogComponent', () => {
  let component: BacklogEditDialogComponent;
  let fixture: ComponentFixture<BacklogEditDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BacklogEditDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BacklogEditDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
