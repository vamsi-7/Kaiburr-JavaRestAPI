package com.vamsi.javaRestAPI.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vamsi.javaRestAPI.model.Testing;
import com.vamsi.javaRestAPI.repository.testingRepo;

@Service
@Transactional
public class TestingServiceImpl implements TestService {
	
	@Autowired
    private testingRepo productRepository;


    @Override
    public Testing createProduct(Testing product) {
        return productRepository.save(product);
    }

    @Override
    public Testing updateProduct(Testing product) throws Throwable{
        Optional < Testing > productDb = this.productRepository.findById(product.getId());

        if (productDb.isPresent()) {
            Testing productUpdate = productDb.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setDescription(product.getDescription());
            productRepository.save(productUpdate);
            return productUpdate;
        } else {
            throw new Exception("Record not found with id : " + product.getId());
        }
    }

    @Override
    public List < Testing > getAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Testing getProductById(long productId) throws Throwable {

        Optional < Testing > productDb = this.productRepository.findById(productId);

        if (productDb.isPresent()) {
            return productDb.get();
        } else {
           throw new Exception("Record not found with id : " + productId);
        }
    }

    @Override
    public void deleteProduct(long productId) throws Exception {
        Optional < Testing > productDb = this.productRepository.findById(productId);

        if (productDb.isPresent()) {
            this.productRepository.delete(productDb.get());
        } else {
        	throw new Exception("Record not found with id : " + productId);
        }

    }
}
