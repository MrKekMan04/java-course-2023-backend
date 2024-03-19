package edu.java.repository.jpa;

import edu.java.entity.GitHubLink;
import org.springframework.data.repository.CrudRepository;

public interface JpaGitHubLinkRepository extends CrudRepository<GitHubLink, Long> {
}
