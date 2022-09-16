package ru.job4j.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PostDBStore;

import java.util.Collection;

@ThreadSafe
@Service
public class PostService {

    private final PostDBStore store;
    private final CityService cityService = new CityService();

    public PostService(PostDBStore store) {
        this.store = store;
    }

    public Collection<Post> findAll() {
        Collection<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return posts;
    }

    public Post add(Post post) {
        return store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}
