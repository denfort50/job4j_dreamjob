package ru.job4j.dream.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post implements Serializable {
    private int id;
    private String name;
    private String description;
    private LocalDateTime created = LocalDateTime.now();
    private boolean visible;
    private City city = new City();

    public Post() { }

    public Post(int id, String name, String description, City city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
    }

    public Post(int id, String name, String description, LocalDateTime created, boolean visible, int cityId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.visible = visible;
        this.city.setId(cityId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
