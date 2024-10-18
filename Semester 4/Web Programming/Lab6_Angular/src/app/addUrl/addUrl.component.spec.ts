import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUrlComponent } from './addUrl.component';

describe('AddUrlComponent', () => {
  let component: AddUrlComponent;
  let fixture: ComponentFixture<AddUrlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddUrlComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddUrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
