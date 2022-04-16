package com.vamsi.javaRestAPI.service;

import java.util.List;

import com.vamsi.javaRestAPI.model.Testing;

public interface TestService {
	Testing createProduct(Testing product);

    Testing updateProduct(Testing product) throws Throwable;

    List < Testing > getAllProduct();

    Testing getProductById(long productId) throws Throwable;

    void deleteProduct(long id) throws Throwable;
}
