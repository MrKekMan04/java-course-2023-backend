package edu.java.repository.jpa;

import edu.java.entity.StackOverflowLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JpaStackOverflowLinkRepository extends CrudRepository<StackOverflowLink, Long> {
    @Modifying
    @Query(value = "INSERT INTO stackoverflow_link (id, answer_count, score) VALUES (?,?,?)", nativeQuery = true)
    void add(Long id, Long answerCount, Long score);
}
