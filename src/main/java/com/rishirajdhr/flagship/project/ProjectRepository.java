package com.rishirajdhr.flagship.project;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents a repository to access and modify {@link Project} entities.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {}
