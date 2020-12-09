package io.security.corespringsecurity2.service;

import io.security.corespringsecurity2.domain.entity.Resources;
import java.util.List;

public interface ResourceService {

    Resources getResources(long id);

    List<Resources> getResources();

    void createResources(Resources resources);

    void deleteResources(Long id);
}
