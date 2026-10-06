package com.orazaka.persistence.domain.ports.inbound;

/**
 * The invariant: <b>a message seen twice is processed once, and a message whose processing failed
 * is seen again.</b>
 *
 * <p>Both halves. This interface used to declare only the first, as {@code isDuplicate} followed by
 * {@code markProcessed}, and four services wrote four answers to it — none of which held (ADR-058
 * §2):
 *
 * <ul>
 *   <li>Three implemented the two-step literally: {@code SELECT EXISTS} and, later, {@code INSERT}.
 *       Check-then-act. Two concurrent deliveries of one message both read "not present" and both
 *       proceed — under the exact condition dedup exists for, because at-least-once redelivery
 *       overlaps a slow first attempt rather than following it.
 *   <li>The fourth claimed atomically and never released, so a message whose handler threw was
 *       marked processed and never came back. It traded a double-process for a silent loss.
 * </ul>
 *
 * <p>The shape below has one author and holds both halves: claim by {@code INSERT}, letting the
 * unique constraint arbitrate, and {@link #release} on failure so the redelivery is reprocessed.
 */
public interface MessageDedupService {

  /**
   * Claims a message for processing, atomically.
   *
   * <p>An {@code INSERT} whose unique constraint decides the race — never a read followed by a
   * write, which is two statements a second consumer can slip between.
   *
   * @param consumer who is processing, so two consumers of one message each get their turn
   * @param messageId the broker's message id; a blank one is claimable, because a producer that
   *     sent no id has asked for no deduplication and must not be silently dropped
   * @return {@code true} when this caller may process it, {@code false} when someone already has
   */
  boolean claim(String consumer, String messageId);

  /**
   * Gives a claim back after processing failed, so redelivery is processed rather than skipped.
   *
   * <p>Without this, {@link #claim} converts a handler exception into a lost message: the row is
   * already committed, the nack redelivers, and the redelivery is refused by the claim its own
   * failed attempt left behind.
   *
   * @param consumer who was processing
   * @param messageId the message to make claimable again
   */
  void release(String consumer, String messageId);
}
