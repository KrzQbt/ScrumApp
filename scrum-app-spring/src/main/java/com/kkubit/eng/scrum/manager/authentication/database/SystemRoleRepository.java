package com.kkubit.eng.scrum.manager.authentication.database;

import com.kkubit.eng.scrum.manager.authentication.model.ESystemRole;
import com.kkubit.eng.scrum.manager.authentication.model.SystemRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemRoleRepository extends JpaRepository<SystemRoleEntity, Integer> {

    Optional<SystemRoleEntity> findByName(ESystemRole name);

}
