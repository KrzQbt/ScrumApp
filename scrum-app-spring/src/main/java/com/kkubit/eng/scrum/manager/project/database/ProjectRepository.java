package com.kkubit.eng.scrum.manager.project.database;


import com.kkubit.eng.scrum.manager.project.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
