package com.vamsi.javaRestAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vamsi.javaRestAPI.model.Testing;

public interface testingRepo extends MongoRepository<Testing, Long> {

}
