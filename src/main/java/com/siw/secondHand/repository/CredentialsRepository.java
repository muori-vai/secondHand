package com.siw.secondHand.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	public Optional<Credentials> findByUsername(String username);

}