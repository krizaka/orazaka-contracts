package com.krizaka.orazaka.persistence.domain.ports.inbound;

import java.util.Map;
import java.util.OptionalInt;

/**
 * The most answers a structured-output call of a <b>feature key</b> may ask a model for (ADR-094
 * §4.6, orazaka-ai-engine#172): the first plus the corrections that name the schema violation. The
 * bound is data an administrator edits, kept in {@code orazaka_feature_structured_bounds} ({@code
 * feature_key}, {@code max_attempts}, 1 to 5), never a constant in code. The row {@code *} is the
 * default of every feature without a row of its own.
 */
public interface FeatureStructuredBoundManager {

  /** The feature key of the default row. */
  String DEFAULT_FEATURE_KEY = "*";

  /** The fewest attempts a bound may allow (the database CHECK says the same). */
  int MIN_ATTEMPTS = 1;

  /** The most attempts a bound may allow (the database CHECK says the same). */
  int MAX_ATTEMPTS = 5;

  /**
   * @param featureKey the feature, e.g. {@code agent.code.issue-to-pr}; {@code null} or blank reads
   *     the default row
   * @return the bound of the feature's own row, else the default row's; empty only when neither
   *     exists, in which case the caller keeps its own safe default
   */
  OptionalInt maxAttempts(String featureKey);

  /**
   * @return every bound by feature key (the default row {@code *} included), sorted by key
   */
  Map<String, Integer> all();

  /**
   * Creates or replaces the bound of a feature (administrator action, audited by the caller).
   *
   * @param featureKey the feature, or {@code *} for the default row; non-blank, at most 120
   *     characters
   * @param maxAttempts from {@link #MIN_ATTEMPTS} to {@link #MAX_ATTEMPTS}
   * @throws IllegalArgumentException when the key is blank or too long or the attempts are out of
   *     range
   */
  void upsert(String featureKey, int maxAttempts);

  /**
   * Removes a feature's own bound so it falls back to the default row. The default row itself is
   * never removed.
   *
   * @return {@code true} when a row was removed
   * @throws IllegalArgumentException when the key is the default row's
   */
  boolean delete(String featureKey);
}
