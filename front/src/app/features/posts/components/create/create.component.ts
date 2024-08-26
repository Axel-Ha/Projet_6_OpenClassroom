import { Component } from '@angular/core';
import { SessionService } from 'src/app/services/session.service';
import { Post } from '../../interfaces/post.interface';
import { PostService } from '../../services/post.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TopicService } from 'src/app/services/topic.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    selector: 'app-create',
    templateUrl: './create.component.html',
    styleUrls: ['./create.component.scss']
  })
  export class CreateComponent {
  
    public topics$ = this.topicService.getTopics();
    public userId = this.sessionService.sessionInformation!.id;
  
    public postForm: FormGroup = this.formBuilder.group({
      topicId: ['', [Validators.required]],
      title: ['', [Validators.required, Validators.maxLength(100)]],
      content: ['', [Validators.required, Validators.maxLength(10000)]]
    });
  
    constructor(
      private router: Router,
      private postService: PostService,
      private sessionService: SessionService,
      private topicService: TopicService,
      private formBuilder: FormBuilder,
      private matSnackBar: MatSnackBar
    ) { }
  
    public submit(): void {
      let post = this.postForm.value as Post;
      post.authorId = this.userId;
      
      this.postService.create(post).subscribe(_ => {
        this.matSnackBar.open('Article créé', 'Fermer', { duration: 4000 });
        this.router.navigate(['/posts']);
      });
    }
  }
  