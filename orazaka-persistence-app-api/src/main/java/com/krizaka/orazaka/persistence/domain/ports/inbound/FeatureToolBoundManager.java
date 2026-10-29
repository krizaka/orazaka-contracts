package com.krizaka.orazaka.persistence.domain.ports.inbound;

import java.util.OptionalInt;

/**
 * The most tool rounds a <b>feature key</b> may run in one turn (ADR-029, orazaka-ai-engine#169):
 * the bound is data an administrator edits, kept in {@code orazaka_feature_tool_bounds} ({@code
 * feature_key}, {@code max_tool_rounds}, 1 to 32), never a constant in code. The row {@code *} is
 * the default of every feature without a row of its own.
 */
public interface FeatureToolBoundManager {

  /** The feature key of the default row. */
  String DEFAULT_FEATURE_KEY = "*";

  /**
   * @param featureKey the feature, e.g. {@code agent.code.issue-to-pr}; {@code null} or blank reads
   *     the default row
   * @return the bound of the feature's own row, else the default row's; empty only when neither
   *     exists, in which case the caller keeps its own safe default
   */
  OptionalInt maxToolRounds(String featureKey);
}
