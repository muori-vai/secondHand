package com.siw.secondHand.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.secondHand.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}