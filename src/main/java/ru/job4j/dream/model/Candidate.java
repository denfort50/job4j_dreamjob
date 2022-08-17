package ru.job4j.dream.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private String desc;
    private Timestamp created;

    public Candidate() { }

    public Candidate(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.created = Timestamp.valueOf(LocalDateTime.now());
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}