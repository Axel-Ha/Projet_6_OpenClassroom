// author-name.component.ts
import { Component, Input, OnChanges } from '@angular/core';
import { Topic } from 'src/app/interfaces/topic.interface';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-topic-name',
  templateUrl: './topic-name.component.html',
  styleUrls: ['./topic-name.component.scss']
})
export class TopicNameComponent implements OnChanges {
  @Input() 
  topicId!: number;
  topicName: string | null = null;

  constructor(private topicService: TopicService) {}

  ngOnChanges(): void {
    this.topicService.getTopic(this.topicId).subscribe((topic: Topic) => {
      this.topicName = topic.name;
    });
  }
}
