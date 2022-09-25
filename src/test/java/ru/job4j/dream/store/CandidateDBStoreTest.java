package ru.job4j.dream.store;

import ru.job4j.Main;
import ru.job4j.dream.model.Candidate;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CandidateDBStoreTest {

    @Test
    void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Denis", "Lead Analyst");
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    void whenFindAllCandidates() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate1 = new Candidate(1, "Denis", "Lead Analyst");
        Candidate candidate2 = new Candidate(2, "Pavel", "C# Developer");
        Candidate candidate3 = new Candidate(3, "Petr", "Java Developer");
        List<Candidate> candidates = new ArrayList<>(List.of(candidate1, candidate2, candidate3));
        store.add(candidate1);
        store.add(candidate2);
        store.add(candidate3);
        List<Candidate> candidatesInDb = store.findAll();
        assertThat(candidatesInDb).containsAll(candidates);
    }

    @Test
    void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidateBefore = new Candidate(1, "Denis", "Lead Analyst");
        Candidate candidateAfter = new Candidate(1, "Denis", "Middle Java Developer");
        store.add(candidateBefore);
        store.update(candidateAfter);
        assertThat(store.findById(1)).isEqualTo(candidateAfter);
    }
}
