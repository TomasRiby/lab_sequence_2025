import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable, take, tap} from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LabSequenceService {

  constructor(private http: HttpClient) {
  }

  get(n: number): Observable<string> {
    return this.http.get<{ value : string }>(`http://localhost:8080/labseq/${n}`)
      .pipe(
        map(res => res.value),
      );
  }
}
