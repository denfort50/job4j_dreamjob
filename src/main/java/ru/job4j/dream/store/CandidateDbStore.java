package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {

    private static final String SELECT_ALL_CANDIDATES = "SELECT * FROM candidate ORDER BY id";
    private static final String INSERT_CANDIDATE = "INSERT INTO candidate(name, description, created, photo) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CANDIDATE = "UPDATE candidate SET name = ?, description = ?, created = ?, photo = ? WHERE id = ?";
    private static final String SELECT_PARTICULAR_CANDIDATE = "SELECT * FROM candidate WHERE id = ?";

    private static final Logger LOG = LogManager.getLogger(PostDBStore.class.getName());

    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ALL_CANDIDATES)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(createCandidate(it));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT_CANDIDATE, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_CANDIDATE)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_PARTICULAR_CANDIDATE)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createCandidate(it);
                }
            }
        } catch (SQLException e) {
            LOG.error("SQLException", e);
        }
        return null;
    }

    private Candidate createCandidate(ResultSet rs) throws SQLException {
        return new Candidate(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getTimestamp("created").toLocalDateTime(),
                rs.getBytes("photo"));
    }
}
