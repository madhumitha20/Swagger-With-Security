package com.yaacreations.swagger.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yaacreations.swagger.dto.DeveloperDto;
import com.yaacreations.swagger.entity.Developer;
import com.yaacreations.swagger.repo.DeveloperRepo;
import com.yaacreations.swagger.service.SwaggerService;

@RestController
public class DeveloperController {
	
	@Autowired
	DeveloperRepo developerRepo;
	
	@Autowired
	SwaggerService service;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/developer/save")
	public String save(@RequestBody Developer developer)
	{
        developer.setPassword(passwordEncoder.encode(developer.getPassword()));
		developerRepo.save(developer);
		return "The given Data Saved Successfully";
	}
	
	@PostMapping("/developer/login")
	public DeveloperDto generateToken(@RequestBody DeveloperDto developer)
	{
		return service.login(developer);
	}
	
	@PutMapping("/developer/update")
	public String update(@RequestBody Developer developer)
	{
		developerRepo.save(developer);
		return "Your Profile Updated Successfully";
	}
	
	@DeleteMapping("/admin/delete/{id}")
	public String delete(@PathVariable int id) {
	    developerRepo.deleteById(id);
	    return "Deleted Successfully";
	}

	@GetMapping("/admin/getAll")
	public List<Developer> viewAll()
	{
		return developerRepo.findAll();	
	}
	
	@GetMapping("/developer/getById/{id}")
	public Optional<Developer> getBYId(@PathVariable int id)
	{
        return developerRepo.findById(id);
	}
}
