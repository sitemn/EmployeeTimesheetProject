package com.site.employeetimesheetproject.repository;

import com.site.employeetimesheetproject.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * ClassName: ProjectRepository
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByName(String name);

    @Query("{ 'employees._id' : ?0 }")
    List<Project> findByEmployeeId(String id);
}
