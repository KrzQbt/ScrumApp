import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoriesCrudComponent } from './stories-crud.component';

describe('StoriesCrudComponent', () => {
  let component: StoriesCrudComponent;
  let fixture: ComponentFixture<StoriesCrudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StoriesCrudComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StoriesCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
