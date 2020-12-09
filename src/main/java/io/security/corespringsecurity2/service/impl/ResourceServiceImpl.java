package io.security.corespringsecurity2.service.impl;

import io.security.corespringsecurity2.domain.entity.Resources;
import io.security.corespringsecurity2.repository.ResourcesRepository;
import io.security.corespringsecurity2.service.ResourceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourcesRepository resourcesRepository;

    @Override
    public Resources getResources(long id) {
        return resourcesRepository.findById(id).orElse(new Resources());
    }

    @Override
    public List<Resources> getResources() {
        return resourcesRepository.findAll();
    }

    @Override
    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
    }

    @Override
    public void deleteResources(Long id) {
        resourcesRepository.deleteById(id);
    }
}
