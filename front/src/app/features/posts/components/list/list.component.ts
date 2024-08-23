import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';

import { Post } from '../../interfaces/post.interface';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent {

  // private userId = this.sessionService.sessionInformation!.id;

  constructor(
    private postService: PostService,
    private sessionService: SessionService,
  ) { 
    console.log("test");
  }
 
  ngOnInit(): void {
    console.log('ListComponent ngOnInit called');
  }
}