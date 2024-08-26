import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';

import { Post } from '../../interfaces/post.interface';
import { PostService } from '../../services/post.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  public posts$: Observable<Post[]> = this.getSortedPosts('desc');
  public sortDirection: 'asc' | 'desc' = 'desc';
  
  constructor(private postService: PostService) {}
 
  public getSortedPosts(sortDirection: 'asc' | 'desc'): Observable<Post[]> {
    return this.postService.getSortedPosts(sortDirection);
  }

  public toggleSort(): void {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    this.posts$ = this.getSortedPosts(this.sortDirection);
  }
}