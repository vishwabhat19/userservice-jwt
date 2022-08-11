package io.getarrays.userservice.repo;

import io.getarrays.userservice.domain.Role;
import io.getarrays.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>
{
	Role findByName(String name);
}
