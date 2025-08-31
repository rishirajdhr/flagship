package com.rishirajdhr.flagship.flag;

import com.rishirajdhr.flagship.flag.exceptions.FlagNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposes REST endpoints to access {@link Flag} entities.
 */
@RestController
@RequestMapping("/flags")
public class FlagController {
  private final FlagService service;

  /**
   * Create a new Flag controller.
   *
   * @param service the {@link Flag} business logic service
   */
  public FlagController(FlagService service) {
    this.service = service;
  }

  /**
   * Create a new flag record.
   *
   * @param newFlag the data for the new flag to be created
   * @return the newly created flag
   */
  @PostMapping
  public Flag createFlag(@RequestBody NewFlag newFlag) {
    return service.createFlag(newFlag.name(), newFlag.description(), newFlag.enabled());
  }

  /**
   * Get a list of all the existing flags.
   *
   * @return feature flags list
   */
  @GetMapping
  public List<Flag> getAllFlags() {
    return service.getAllFlags();
  }

  /**
   * Get a feature flag by its name.
   *
   * @param name the name of the feature flag
   * @return the {@link Flag} with the given name
   * @throws RuntimeException if no flag with the given name exists
   */
  @GetMapping("/{name}")
  public Flag getFlagByName(@PathVariable String name) throws RuntimeException {
    return service
        .getFlagByName(name)
        .orElseThrow(() -> new FlagNotFoundException(name));
  }
}
