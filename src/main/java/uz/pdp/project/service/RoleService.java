package uz.pdp.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.project.entity.Role;
import uz.pdp.project.repo.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void saveAll(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

}
