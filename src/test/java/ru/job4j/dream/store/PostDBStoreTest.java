package ru.job4j.dream.store;

import ru.job4j.Main;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1, "Java Job", "Java Description", new City(1, "Murmansk"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post1 = new Post(1, "Java Job", "Java Description", new City(1, "Murmansk"));
        Post post2 = new Post(2, "C# Job", "C# Description", new City(2, "Moscow"));
        Post post3 = new Post(3, "Golang Job", "Golang Description", new City(3, "Ufa"));
        List<Post> posts = new ArrayList<>(List.of(post1, post2, post3));
        store.add(post1);
        store.add(post2);
        store.add(post3);
        List<Post> postsInDb = store.findAll();
        assertThat(postsInDb).containsAll(posts);
    }

    @Test
    void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post postBefore = new Post(1, "Java Job", "Java Description", new City(3, "Ufa"));
        Post postAfter = new Post(1, "C# Job", "C# Description", new City(2, "Moscow"));
        store.add(postBefore);
        store.update(postAfter);
        assertThat(store.findById(1)).isEqualTo(postAfter);
    }
}
