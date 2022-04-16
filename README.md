
# Kaiburr-JavaRestAPI

Implemention an application in java which provides a REST API with endpoints for searching,
creating and deleting “server” objects.“Server” objects are stored in MongoDB database.
Be sure that you can show how application responds to requests using postman.

## Requirements

    JDK 1.8 or above
    MongoDB 
    Apache Maven 3
    IDE - STS or IntelliJ Idea

## Installation

Run MongoDB database

```bash
  1. type mongod in cmd
  2. type mongo.exe
```

Create a project using [Spring Intializer](https://start.spring.io/)

```bash
  Add Dependencies

  Spring Web
  Srping Data MongoDB
  Lombak

  And click on Generate
```
## Code Snippets

1.Maven Dependencies

Need to add below dependencies to enable Mongo related config in pom.xml. Lombok's dependency is to get rid of boiler-plate code.

MongoDB is a very famous noSql database that supports reactive programming.

```xml
  
  <dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

```

2. Properties file

In src/main/resources/application.properties the application.properties files, we provide the connection url, the jdbc driver, the username, the password and all the database settings

```bash
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=Test

```

3. Model class

Below are the model classes which we will store in MongoDB and perform CRUD operations.
com.vamsi.javaRestAPI.model.Testing.java

@Document : to make the class a MongoDB document

@Id : to make a field a primary key

@Indexed: This creates a unique index on name field.

@NonNull: This makes sure the filed is not blank

```java
package com.vamsi.javaRestAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString

@Document(collection = "test")
public class Testing {
	
	@Id
    private long id;

    @NonNull
    @Indexed(unique = true)
    private String name;
    private String description;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	
}

```

4. Repository Component

Creating Repository for accessing data from the database. 

Extending MongoRepository<Class, ID> interface which enables CRUD related methods.

```java
package com.vamsi.javaRestAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vamsi.javaRestAPI.model.Testing;

public interface testingRepo extends MongoRepository<Testing, Long> {

}

```

5 Service Component

In service we will connect with the repository for crud operations.

Create interface TestService.java

```java
import java.util.List;

import com.vamsi.javaRestAPI.model.Testing;

public interface TestService {
	Testing createProduct(Testing product);

    Testing updateProduct(Testing product) throws Throwable;

    List < Testing > getAllProduct();

    Testing getProductById(long productId) throws Throwable;

    void deleteProduct(long id) throws Throwable;
}
```

Create class TestServiceImpl.java to implement all the mentods defined in the interface

```java
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
```

6 Controller Component

Finally, creating the APIs which will be exposed to the users.

API Endpoints

API CRUD Operations

    GET Mapping http://localhost:8080/students - Get all Students

    GET Mapping http://localhost:8080/student/1 - Get Student by ID

    POST Mapping http://localhost:8080/addStudent - Add new Student in DB

Request Body for POST Mapping
```json
{
    "id": "1",
    "name": "Vamsi",
    "description": "I am a passionate software engineering graduate specialized in Mtech integrated program from VIT Chennai"
}
```
PUT Mapping http://localhost:8080/student/1 - Update existing Student for given ID
```json
{
	"id": 2,
    "name": "Karthik",
    "description": "Friend of Vamsi"
}
```

DELETE Mapping http://localhost:8080/student/1 - Delete Student by ID

## Running Spring boot application

This spring boot application has an entry point Java class called JavaRestApiApplication.java with the public static void main(String[] args) method, which you can run to start the application.

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaRestApiApplication.class, args);
	}

}
```


## Test REST APIs Using Postman Client

Create Student - Post HTTP Request

![POST Request](https://github.com/vamsi-7/Kaiburr-JavaRestAPI/blob/main/javaRestAPI/Screenshots/POST_req.JPG?raw=true)

Get All Students - Get HTTP Request

![POST Request](https://github.com/vamsi-7/Kaiburr-JavaRestAPI/blob/main/javaRestAPI/Screenshots/GET_ALL.JPG?raw=true)

Get Student By Id - Get HTTP Request

![POST Request](https://github.com/vamsi-7/Kaiburr-JavaRestAPI/blob/main/javaRestAPI/Screenshots/GET_ByID.JPG?raw=true)

Update Student - PUT HTTP Request

![POST Request](https://github.com/vamsi-7/Kaiburr-JavaRestAPI/blob/main/javaRestAPI/Screenshots/PUT_ByID.JPG?raw=true)

Delete Student - DELETE HTTP Request

![POST Request](https://github.com/vamsi-7/Kaiburr-JavaRestAPI/blob/main/javaRestAPI/Screenshots/DELETE_req.JPG?raw=true)

Prints "OK" after the record is deleted.


## Database (MongoDB) After all CRUD operations

![POST Request](https://github.com/vamsi-7/Kaiburr-JavaRestAPI/blob/main/javaRestAPI/Screenshots/Database_afterAllOperations.JPG?raw=true)
