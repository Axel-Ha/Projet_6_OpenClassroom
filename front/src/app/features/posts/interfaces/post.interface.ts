import { User } from "src/app/interfaces/user.interface";
import { Topic } from "../../../pages/topics/topic.interface";

export interface Post {
    id: number,
    topicId: number,
    title: string,
    authorId: number,
    content: string,
    createdAt: Date
}