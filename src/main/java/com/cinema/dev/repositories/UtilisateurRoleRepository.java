package com.cinema.dev.repositories;

import com.cinema.dev.models.UtilisateurRole;
import com.cinema.dev.models.UtilisateurRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UtilisateurRoleRepository extends JpaRepository<UtilisateurRole, UtilisateurRoleId>, JpaSpecificationExecutor<UtilisateurRole> {

}
