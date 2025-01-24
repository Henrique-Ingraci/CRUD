package com.Crud.Crud.repositories;

import com.Crud.Crud.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
