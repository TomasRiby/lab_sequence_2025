import {ChangeDetectorRef, Component} from '@angular/core';
import {LabSequenceService} from '../lab-sequence-service';
import {FormsModule} from '@angular/forms';


@Component({
  selector: 'app-lab-sequence',
  imports: [
    FormsModule
  ],
  templateUrl: './lab-sequence.html',
  styleUrl: './lab-sequence.css'
})
export class LabSequence {
  n = 0;
  result: string | null = null;
  loading = false;

  constructor(private labSequenceService: LabSequenceService, private cdr: ChangeDetectorRef
              ) {
  }

  compute() {
    this.result = null;
    this.loading = true
    this.cdr.markForCheck();

    this.labSequenceService.get(this.n).subscribe({
      next: (value) => {
        console.log('Received value:', value);
        this.result = value;
        this.loading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        window.alert("Memory limit exceeded, try a smaller number");
        this.loading = false;
      }
    });
  }
}
