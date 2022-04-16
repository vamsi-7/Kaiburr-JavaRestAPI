package com.vamsi.javaRestAPI.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vamsi.javaRestAPI.model.Testing;
import com.vamsi.javaRestAPI.service.TestService;

@RestController
public class TestingControl {

//	@Autowired
//	private testingRepo repository;
//
//	@PostMapping("/add")
//	public String saveBook(@RequestBody Testing book) {
//		repository.save(book);
//		return "Added rec";
//	}
//
//	@GetMapping("/findAll")
//	public List<Testing> getBooks() {
//		return repository.findAll();
//	}
//
//	@GetMapping("/findAll/{id}")
//	public Optional<Testing> getBook(@PathVariable int id) {
//		return repository.findById(id);
//	}
//
//	@DeleteMapping("/delete/{id}")
//	public String deleteBook(@PathVariable int id) {
//		repository.deleteById(id);
//		return "book deleted with id : " + id;
//	}
	
	@Autowired
    private TestService productService;

	@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/products")
    public ResponseEntity < List < Testing >> getAllProduct() {
        return ResponseEntity.ok().body(productService.getAllProduct());
    }
	@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/products/{id}")
    public ResponseEntity < Testing > getProductById(@PathVariable long id) throws Throwable {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }
	@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/products")
    public ResponseEntity < Testing > createProduct(@RequestBody Testing product) {
        return ResponseEntity.ok().body(this.productService.createProduct(product));
    }
	@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/products/{id}")
    public ResponseEntity < Testing > updateProduct(@PathVariable long id, @RequestBody Testing product) throws Throwable {
        product.setId(id);
        return ResponseEntity.ok().body(this.productService.updateProduct(product));
    }
	@CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/products/{id}")
    public HttpStatus deleteProduct(@PathVariable long id) throws Throwable {
        this.productService.deleteProduct(id);
        return HttpStatus.OK;
    }

}