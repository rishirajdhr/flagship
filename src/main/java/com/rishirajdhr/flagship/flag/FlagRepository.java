package com.rishirajdhr.flagship.flag;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents a repository to access and modify {@link Flag} entities.
 */
public interface FlagRepository extends JpaRepository<Flag, Long> {}
