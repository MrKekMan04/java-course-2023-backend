package edu.java.repository.jpa;

import edu.java.entity.GitHubLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JpaGitHubLinkRepository extends CrudRepository<GitHubLink, Long> {
    @Modifying
    @Query(value = "INSERT INTO github_link (id, default_branch, forks_count) VALUES (?,?,?)", nativeQuery = true)
    void add(Long id, String defaultBranch, Long forksCount);
}
