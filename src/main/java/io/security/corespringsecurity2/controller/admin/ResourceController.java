package io.security.corespringsecurity2.controller.admin;

import io.security.corespringsecurity2.domain.dto.ResourcesDto;
import io.security.corespringsecurity2.domain.entity.Resources;
import io.security.corespringsecurity2.domain.entity.Role;
import io.security.corespringsecurity2.repository.RoleRepository;
import io.security.corespringsecurity2.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import io.security.corespringsecurity2.service.ResourceService;
import io.security.corespringsecurity2.service.RoleService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @GetMapping(value = "/admin/resources")
    public String getResources(Model model) {
        List<Resources> resources = resourceService.getResources();
        model.addAttribute("resources", resources);
        return "admin/resource/list";
    }

    @PostMapping(value = "/admin/resources")
    public String createResources(ResourcesDto resourcesDto) {
        ModelMapper modelMapper = new ModelMapper();
        Role role = roleRepository.findByRoleName(resourcesDto.getRoleName());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Resources resources = modelMapper.map(resourcesDto, Resources.class);
        resources.setRoleSet(roles);
        resourceService.createResources(resources);

        // TODO: 이건 자세히 봐야 함
        if ("url".equals(resourcesDto.getResourceType())) {
            urlFilterInvocationSecurityMetadataSource.reload();
        } else {

        }
        return "redirect:/admin/resources";
    }

    @GetMapping(value = "/admin/resources/register")
    public String viewRoles(Model model) {
        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);

        ResourcesDto resourcesDto = new ResourcesDto();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role());
        resourcesDto.setRoleSet(roleSet);
        model.addAttribute("resources", resourcesDto);

        return "admin/resource/detail";
    }

    @GetMapping(value = "/admin/resources/{id}")
    public String getResources(@PathVariable String id, Model model) {
        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);
        Resources resources = resourceService.getResources(Long.valueOf(id));

        ModelMapper modelMapper = new ModelMapper();
        ResourcesDto resourcesDto = modelMapper.map(resources, ResourcesDto.class);
        model.addAttribute("resources", resources);
        return "admin/resource/detail";
    }

    @GetMapping(value = "/admin/resources/delete/{id}")
    public String removeResources(@PathVariable Long id, Model model) {
        Resources resources = resourceService.getResources(id);
        resourceService.deleteResources(id);

        // TODO
        if ("url".equals(resources.getResourceType())) {
            urlFilterInvocationSecurityMetadataSource.reload();
        } else {

        }

        return "redirect:/admin/resources";
    }
}
