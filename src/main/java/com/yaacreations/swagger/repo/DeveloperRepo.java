package com.yaacreations.swagger.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yaacreations.swagger.entity.Developer;


@Repository
public interface DeveloperRepo extends JpaRepository<Developer, Integer>{

	Developer findByEmail(String email);

}
