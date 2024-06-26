package edu.java.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponseDTO(
    @JsonProperty("full_name") String fullName,
    @JsonProperty("updated_at") OffsetDateTime updatedAt,
    @JsonProperty("default_branch") String defaultBranch,
    @JsonProperty("forks_count") Long forksCount
) {
}
