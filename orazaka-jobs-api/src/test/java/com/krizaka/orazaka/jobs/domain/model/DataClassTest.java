package com.krizaka.orazaka.jobs.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DataClassTest {

  @Test
  void aProtectedJobIsKeptNotOneMomentPastTerminal() {
    assertEquals(Optional.of(Duration.ZERO), DataClass.SENSITIVE.jobRetentionAfterTerminal());
    assertEquals(Optional.of(Duration.ZERO), DataClass.REGULATED.jobRetentionAfterTerminal());
  }

  @Test
  void anOrdinaryJobIsNotPurgedByThisWindow() {
    assertEquals(Optional.empty(), DataClass.STANDARD.jobRetentionAfterTerminal());
  }

  @Test
  void onlyTheProtectedClassesSwitchOnTheControls() {
    assertFalse(DataClass.STANDARD.isProtected());
    assertTrue(DataClass.SENSITIVE.isProtected());
    assertTrue(DataClass.REGULATED.isProtected());
  }
}
