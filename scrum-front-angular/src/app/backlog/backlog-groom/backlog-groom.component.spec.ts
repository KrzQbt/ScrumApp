import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacklogGroomComponent } from './backlog-groom.component';

describe('BacklogGroomComponent', () => {
  let component: BacklogGroomComponent;
  let fixture: ComponentFixture<BacklogGroomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BacklogGroomComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BacklogGroomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
