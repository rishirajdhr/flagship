package com.rishirajdhr.flagship.project;

import com.rishirajdhr.flagship.auth.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Represents a repository to access and modify {@link Project} entities.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
  /**
   * Retrieve the projects for an application user.
   *
   * @param appUser the application user
   * @return a list of projects owned by the application user
   */
  List<Project> findAllByOwner(AppUser appUser);
}
