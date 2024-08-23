import { User } from "src/app/interfaces/user.interface";
import { Topic } from "../../../pages/topics/topic.interface";

export interface Post {
    post_id: number,
    topics: Topic[];
    title: string,
    user: User,
    content: string,
    created_at: Date
}