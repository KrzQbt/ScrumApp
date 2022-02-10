import { Component, Input, OnInit } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { RequestService } from 'src/app/services/request.service';


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  

  constructor(private requestService:RequestService) { }

  ngOnInit(): void {
  }
  

  // https://stackoverflow.com/questions/37520170/ngfor-repeating-components

  tasks:any = [
    {
      'id':'1',
      'desc': 'some task',
      'status':'done',
      'ownerId':'none',
      'storyId':'12'
    },
    {
      'id':'12',
      'desc': 'some task',
      'status':'free',
      'ownerId':'11',
      'storyId':'12'
    },
    {
      'id':'22',
      'desc': 'some task',
      'status':'taken',
      'ownerId':'32',
      'storyId':'12'
    }
  ];



}
