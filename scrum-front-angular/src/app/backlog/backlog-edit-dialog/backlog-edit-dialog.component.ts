import { Component, Inject, OnInit, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-backlog-edit-dialog',
  templateUrl: './backlog-edit-dialog.component.html',
  styleUrls: ['./backlog-edit-dialog.component.css']
})
export class BacklogEditDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<BacklogEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

    onNoClick(): void {
      this.dialogRef.close();
    }

    pbiTypes = ["Feature", "Change","Fix", "Technical improvement","Knowledge aquisition"];
  
}
