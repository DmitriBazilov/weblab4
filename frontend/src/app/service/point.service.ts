import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {PointRequestDto} from "../dto/point-request-dto";
import {Observable} from "rxjs";
import {PointResponse} from "../dto/point-response";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PointService {

  private getUrl = 'http://localhost:8080/backend-1.0-SNAPSHOT/api/hits';
  private saveUrl = 'http://localhost:8080/backend-1.0-SNAPSHOT/api/hits/save';
  private clearUrl = 'http://localhost:8080/backend-1.0-SNAPSHOT/api/hits/clear';


  savePoint(point: PointRequestDto): Observable<PointResponse> {
    return this.http.post<PointResponse>(this.saveUrl, point, httpOptions);
  }

  getPoints(): Observable<PointResponse[]> {
    return this.http.get<PointResponse[]>(this.getUrl);
  }

  clearPoints(): void {
    this.http.get(this.clearUrl).subscribe(data => {
      console.log(data);
    });
  }

  constructor(private http: HttpClient) {
  }
}
