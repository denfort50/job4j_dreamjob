package ru.job4j.dream.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.service.CityService;
import ru.job4j.dream.service.PostService;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    public void whenPostsThenSuccess() {
        List<Post> posts = Arrays.asList(
                new Post(1, "Java developer", "Java, Spring, Hibernate",
                        new City(1, "Москва")),
                new Post(2, "DBA", "Oracle DB, PL/SQL Developer, Linux",
                        new City(2, "Тольятти")));
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        when(postService.findAll()).thenReturn(posts);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenCreatePostThenSuccess() {
        Post input = new Post(1, "Java developer", "Java, Spring, Hibernate",
                new City(1, "Москва"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    void whenAddPostThenSuccess() {
        Post post = new Post(1, "Java developer", "Java, Spring, Hibernate",
                        new City(1, "Москва"));
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        postService.add(post);
        String page = postController.addPost(model);
        verify(postService).add(post);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    void whenUpdatePostThenSuccess() {
        Post post = new Post(1, "Java developer", "Java, Spring, Hibernate",
                new City(1, "Москва"));
        Post updatedPost = new Post(1, "Oracle developer", "Oracle DB",
                new City(2, "Санкт-Петербург"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        postService.add(post);
        String page = postController.updatePost(updatedPost);
        verify(postService).update(updatedPost);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    void whenFormUpdatePostThenSuccess() {
        Post post = new Post(1, "Java developer", "Java, Spring, Hibernate",
                new City(1, "Москва"));
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(postService.findById(post.getId())).thenReturn(post);
        when(cityService.getAllCities()).thenReturn(List.of(new City(1, "Москва")));
        PostController postController = new PostController(postService, cityService);
        postService.add(post);
        String page = postController.formUpdatePost(model, post.getId());
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", List.of(new City(1, "Москва")));
        assertThat(page).isEqualTo("updatePost");
    }
}