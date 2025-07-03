import { Component } from '@angular/core';
import {LabSequence} from './lab-sequence/lab-sequence';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [LabSequence],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'Frontend';
}
