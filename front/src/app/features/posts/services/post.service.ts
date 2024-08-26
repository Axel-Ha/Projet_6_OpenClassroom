import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Post } from '../interfaces/post.interface';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  constructor(private httpClient: HttpClient) { }

  private apiUrl = 'api/post';

  public detail(id: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.apiUrl}/${id}`);
  }
  public getPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.apiUrl);
  }

  public create(post: Post): Observable<Post> {
    console.log(post);
    return this.httpClient.post<Post>(this.apiUrl, post);
  }

  public getSortedPosts(sortDirection: 'asc' | 'desc'): Observable<Post[]> {
    return this.getPosts().pipe(
      map(posts => {
        return posts.sort((a, b) => {
          const date1 = new Date(a.createdAt).getTime();
          const date2 = new Date(b.createdAt).getTime();
          return sortDirection === 'asc' ? date1 - date2 : date2 - date1;
        });
      })
    );
  }
}