import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) {}

  private apiUrl = 'api/comment'

  public getComments(id: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.apiUrl}/${id}`);
  }

  public createCommente(comment: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(this.apiUrl,comment);
  }

}