package com.example.ecommerceapi.init;

import com.example.ecommerceapi.domain.Authority;
import com.example.ecommerceapi.domain.Role;
import com.example.ecommerceapi.feature.user.AuthorityRepository;
import com.example.ecommerceapi.feature.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    void initData(){
        roleInit();
        authorityInit();
        roleAuthorityInit();
    }

    void roleInit(){
        List<String> roles = List.of("ADMIN","USER");
        if (roleRepository.count() == 0){
            roles.forEach(role -> {
                Role newRole = new Role();
                newRole.setName(role);
                roleRepository.save(newRole);
            });
        }
    }

    void authorityInit(){
        List<String> authorities = List.of("READ","WRITE","DELETE");
        if (authorityRepository.count() == 0){
            authorities.forEach(authority ->{
                Authority newAuthority = new Authority();
                newAuthority.setName(authority);
                authorityRepository.save(newAuthority);
            });
        }
    }


    void roleAuthorityInit(){
        // Fetch all roles and authorities
        List<Role> roles = new ArrayList<>(roleRepository.findAll());
        List<Authority> authorities = new ArrayList<>(authorityRepository.findAll());

        // Assign authorities to roles
        for (Role role : roles) {
            try {
                switch (role.getName()) {
                    case "USER":
                        role.setAuthorities(filterAuthorities(authorities, "READ","WRITE"));
                        break;
                    case "ADMIN":
                        role.setAuthorities(authorities); // Admin has all authorities
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected role: " + role.getName());
                }
                // Save the role back to the repository
                roleRepository.save(role);
            } catch (Exception e) {
                System.out.println("Error assigning authorities to role: " + role.getName());
                e.printStackTrace();
            }
        }
    }

    private List<Authority> filterAuthorities(List<Authority> authorities, String... names) {
        List<Authority> filtered = new ArrayList<>();
        for (Authority authority : authorities) {
            for (String name : names) {
                if (authority.getName().equals(name)) {
                    filtered.add(authority);
                }
            }
        }
        return filtered;
    }
}
