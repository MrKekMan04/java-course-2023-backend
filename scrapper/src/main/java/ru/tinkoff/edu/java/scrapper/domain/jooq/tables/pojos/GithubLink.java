/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;

import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class GithubLink implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String defaultBranch;
    private Long forksCount;

    public GithubLink() {}

    public GithubLink(GithubLink value) {
        this.id = value.id;
        this.defaultBranch = value.defaultBranch;
        this.forksCount = value.forksCount;
    }

    @ConstructorProperties({ "id", "defaultBranch", "forksCount" })
    public GithubLink(
        @NotNull Long id,
        @Nullable String defaultBranch,
        @Nullable Long forksCount
    ) {
        this.id = id;
        this.defaultBranch = defaultBranch;
        this.forksCount = forksCount;
    }

    /**
     * Getter for <code>GITHUB_LINK.ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>GITHUB_LINK.ID</code>.
     */
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>GITHUB_LINK.DEFAULT_BRANCH</code>.
     */
    @Size(max = 64)
    @Nullable
    public String getDefaultBranch() {
        return this.defaultBranch;
    }

    /**
     * Setter for <code>GITHUB_LINK.DEFAULT_BRANCH</code>.
     */
    public void setDefaultBranch(@Nullable String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    /**
     * Getter for <code>GITHUB_LINK.FORKS_COUNT</code>.
     */
    @Nullable
    public Long getForksCount() {
        return this.forksCount;
    }

    /**
     * Setter for <code>GITHUB_LINK.FORKS_COUNT</code>.
     */
    public void setForksCount(@Nullable Long forksCount) {
        this.forksCount = forksCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final GithubLink other = (GithubLink) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.defaultBranch == null) {
            if (other.defaultBranch != null)
                return false;
        }
        else if (!this.defaultBranch.equals(other.defaultBranch))
            return false;
        if (this.forksCount == null) {
            if (other.forksCount != null)
                return false;
        }
        else if (!this.forksCount.equals(other.forksCount))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.defaultBranch == null) ? 0 : this.defaultBranch.hashCode());
        result = prime * result + ((this.forksCount == null) ? 0 : this.forksCount.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GithubLink (");

        sb.append(id);
        sb.append(", ").append(defaultBranch);
        sb.append(", ").append(forksCount);

        sb.append(")");
        return sb.toString();
    }
}
