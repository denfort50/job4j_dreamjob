package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger(4);

    private PostStore() {
        posts.put(1, new Post(1,
                "Junior Java Job",
                "Java Core, Maven, SQL, Git")
        );
        posts.put(2, new Post(2,
                "Middle Java Job",
                "Java Core, Maven, SQL, Git, Spring, Hibernate"));
        posts.put(3, new Post(3,
                "Senior Java Job",
                "Java Core, Maven, SQL, Git, Spring, Hibernate, Kafka, Docker, Kubernetes, Microservices"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post add(Post post) {
        post.setId(id.getAndIncrement());
        return posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Post update(Post post) {
        return posts.replace(post.getId(), post);
    }

}
