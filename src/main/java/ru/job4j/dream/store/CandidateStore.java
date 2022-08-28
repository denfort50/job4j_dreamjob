package ru.job4j.dream.store;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dream.model.Candidate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger(4);

    private CandidateStore() {
        candidates.put(1, new Candidate(1,
                "Tomas Anderson",
                "Junior Java Developer")
        );
        candidates.put(2, new Candidate(2,
                "Neo",
                "Middle Java Developer"));
        candidates.put(3, new Candidate(3,
                "John Wick",
                "Senior Java Developer"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate add(Candidate candidate) {
        candidate.setId(id.getAndIncrement());
        return candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public Candidate update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate);
    }
}
