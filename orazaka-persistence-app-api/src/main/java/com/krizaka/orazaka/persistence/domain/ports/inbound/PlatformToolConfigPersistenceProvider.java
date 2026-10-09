package com.krizaka.orazaka.persistence.domain.ports.inbound;

import com.krizaka.orazaka.persistence.domain.model.PlatformToolConfigDto;
import java.util.Optional;

/** Port interface for managing PlatformToolConfig persistence operations. */
public interface PlatformToolConfigPersistenceProvider {

  Optional<PlatformToolConfigDto> findByToolId(String toolId);

  PlatformToolConfigDto save(PlatformToolConfigDto configDto);
}
