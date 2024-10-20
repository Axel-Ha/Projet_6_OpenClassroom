import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';

import { Post } from '../../interfaces/post.interface';
import { PostService } from '../../services/post.service';
import { UserService } from 'src/app/services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Comment } from 'src/app/interfaces/comment.interface';
import {CommentService} from 'src/app/services/comment.service';

@Component({
  selector: 'app-list',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss'],
})
export class DetailComponent implements OnInit {

  public post: Post | undefined;
  public postId: number;
  public comments$: Observable<Comment[]> | undefined;
  public userId: number;
  public commentForm: FormGroup | undefined = this.formBuider.group({
    message: ['', [Validators.required, Validators.maxLength(2000)]]
  });


  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private sessionService: SessionService,
    private commentService: CommentService,
    private formBuider: FormBuilder,
    private matSnackBar: MatSnackBar,
    private router: Router
  ) {
    this.postId = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.userId = this.sessionService.sessionInformation!.id;
  }

  ngOnInit(): void {
    this.fetchComments();
    this.postService.detail(this.postId).subscribe({
      next: (post: Post) => {
        this.post = post;
      },
      error: _ => this.router.navigate(['not-found'])
    })
  }

  public fetchComments(): void{
    this.comments$ = this.commentService.getComments(this.postId);
  }

  public sendComment(): void{
    let comment = this.commentForm?.value as Comment;
    comment.userId = this.userId;
    comment.postId = this.postId;
    this.commentService.createCommente(comment).subscribe({
      next: _ => {
        this.matSnackBar.open('Comment created', 'Close', { duration: 3000 });
        this.comments$ = this.commentService.getComments(this.postId);
        this.fetchComments();
      },
      error: _ => this.matSnackBar.open('Error creating comment', 'Close', { duration: 3000 })
    });
  }
}

