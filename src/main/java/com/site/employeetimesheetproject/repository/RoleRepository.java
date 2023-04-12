package com.site.employeetimesheetproject.repository;

import com.site.employeetimesheetproject.model.ERole;
import com.site.employeetimesheetproject.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * ClassName: RoleRepository
 * Package: com.site.employeetimesheetproject.repository
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(ERole name);

}
