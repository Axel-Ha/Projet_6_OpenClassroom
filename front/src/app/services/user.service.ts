import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { Topic } from '../interfaces/topic.interface';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {


    private pathService = 'api/user';
    private subscriptionsSubject = new BehaviorSubject<Topic[]>([]);
    public subscriptions$ = this.subscriptionsSubject.asObservable();
  
    constructor(private httpClient: HttpClient) { }
  
    public getUser(id: number): Observable<User> {
      return this.httpClient.get<User>(`${this.pathService}/${id}`);
    }
  
    public update(id: number, user: User): Observable<SessionInformation> {
      return this.httpClient.put<{ body: SessionInformation }>(`${this.pathService}/${id}`, user)
        .pipe(
          map(response => response.body) // Extracts the body from the response
        );
    }
  
    public getSubscriptions(id: number): Observable<Topic[]> {
      return this.httpClient.get<Topic[]>(`${this.pathService}/${id}/subscriptions`).pipe(
        tap(topics => this.subscriptionsSubject.next(topics))
      );
    }
  
    public subscribe(id: number, idTopic: number): Observable<void> {
      return this.httpClient.put<void>(`${this.pathService}/${id}/subscribe/${idTopic}`, null);
    }
  
    public unsubscribe(id: number, idTopic: number): Observable<void> {
      return this.httpClient.delete<void>(`${this.pathService}/${id}/subscribe/${idTopic}`);
    }
  
  }