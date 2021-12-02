import {Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JsonService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/';
    //this.url = '';
  }

  getJson(url: string): Observable<any> {
    let options: Object = {
      "observe": 'body',
      "responseType": 'text'
    }
    url = this.url + url;
    return this.http.get(url, options);
  }

  getJsonP(url: string, params: HttpParams): Observable<any> {
    let headers = new HttpHeaders();
    headers.append("Content-Type", "aplication/json");
    headers.append("observe", "body");

    url = this.url + url;
    return this.http.get(url, {headers, params, responseType: 'text'});
  }

  getJsonPJ(url: string, params: HttpParams): Observable<any> {
    let headers = new HttpHeaders();
    headers.append("Content-Type", "aplication/json");
    headers.append("observe", "body");

    url = this.url + url;
    return this.http.get(url, {headers, params, responseType: 'json'});
  }


  putJsonVacunas(url: string, parameter1: string, parameter2: number) {
    let options: Object = {
      "observe": 'body',
      "responseType": 'text'
    }
    url = this.url + url + "/" + parameter1 + "/" + parameter2;
    return this.http.put(url, options);
  }

  postJson(url: string, body: Object) {
    let options: Object = {
      "observe": 'body',
      "responseType": 'json'
    }
    url = this.url + url;
    return this.http.post(url, body, options);
  }

  postJsonCrearCentro(url: string, body: Object): Observable<any> {
    let options: Object = {
      "observe": 'body',
      "responseType": 'text'
    }
    url = this.url + url;
    return this.http.post(url, body, options);
  }


  postJsonUpdateCS(url: string, body: Object, options: Object) {
    url = this.url + url;
    return this.http.post(url, body, options);
  }

  deleteJson(url: string, parameter1: string) {
    let options: Object = {
      "observe": 'body',
      "responseType": 'json'
    }
    url = this.url + url + "/" + parameter1;
    return this.http.delete(url, options);
  }

  login(url: string, body: Object): Observable<any> {
    let options: Object = {
      "observe": 'body',
      "responseType": 'json'
    }
    url = this.url + url;
    return this.http.post(url, body, options);
  }
}
