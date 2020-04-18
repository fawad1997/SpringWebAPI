# Java Spring Web API
In this repository, you can learn **Spring Rest API** from beginner to advanced level.
### Quick Links
- [Creating Project](#creating-project)
- [CRUD with H2 Database](#crud-with-h2-database)
  - [Creating Models/Tables/Entities](#creating-models-tables-entities)
  - [Creating Repository](#creating-repository)
  - [Creating Service](#creating-service)
  - [Creating Controller](#creating-controller)
    - [Enabling Cross-Origin](#enabling-cross-origin)
- [Using MySQL database instead of H2](#using-mysql-database-instead-of-h2)
- [One to Many Relation in Hibernate](#one-to-many-relation-in-hibernate)
  - [Creating Entities](#creating-entities)
- [Error Handling](#error-handling)
- [Creating Filters](#creating-filters)
- [Spring Security](#spring-security)
    - [Spring Basic Security](#spring-basic-security)
        - [Create table for application users](#create-table-for-application-users)

## Creating Project
In IntelliJ IDEA, go to spring initilizer, create new project by selecting **Spring web** in dependencies. [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/ee38d2323931446cb310ba963d825503ae73a6a4)
![](/gitimages/projectcreate.jpg)
Give it proper name at create it, it may take few minutes for downloading the dependencies.
Create a package for controllers in src>main>java>[your pakage name]/controller [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/63116f88c2c81305a20e53b795142dd6a3bd47c8)

Add a controller to display hello world message. **@RestController** makes it restfull controller and **@RequestMapping(value = "/hello")** defines URL mapping
```java
@RestController
public class HelloController {
    @RequestMapping(value = "/hello")
    public String sayHello(){
        return "Hello World!";
    }
}
```

## CRUD with H2 Database
to use database we need few dependencies to be installed
Go to [mavenrepository](https://mvnrepository.com/)</a>
and search for the following dependencies and add it to your [pom.xml](https://github.com/fawad1997/SpringWebAPI/blob/master/pom.xml) _dependencies_ section
Please donot include test scope as we will not be doing testing at that stage [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/ba8bd9484b4901e214a9d1242c4c758c03489524)
<ul><li><a href="https://mvnrepository.com/artifact/org.projectlombok/lombok"> Lombok</a> </li>
<li><a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa">Data JPA Starter </a></li>
<li><a href="https://mvnrepository.com/artifact/com.h2database/h2"> H2 Database</a></li>
</ul>
Now you have added a database named H2 database(in-memory db), you need to specify database name too

1. Open [application.properties](https://github.com/fawad1997/SpringWebAPI/blob/master/src/main/resources/application.properties) in resources folder
2. specify database name as follows

```
spring.datasource.url=jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE
spring.jpa.hibernate.ddl-auto=update
```
### Creating Models-Tables-Entities
Create a package **entity** where you will create Entity classes
e.g we are creating **Person** entity [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/2c70939ef61ba30a905e4ecb2d0c4ecd723497aa)

Use **@Entity** annotation to make it entity, **@Data** to make setter getters, **@NoArgsConstructor** to create no argument constructor, **@AllArgsConstructor** to make argument constructor. **@Id** to make it primary key, **@GeneratedValue(strategy = GenerationType.AUTO)** for autoincrement.
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String Name;
    private int Age;
    private double Height;
    private String CNIC;
}
```
Now your table will be created once the application starts.

### Creating Repository
Now create a repository for every entity, create package named **repository** and create interface,, e.g. PersonRepository that will extend **JpaRepository<Person,Integer>** and use **@Repository** annotation on it. [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/d830dfddcb418ecfe81741f0842342eebb69903f)
```java
@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
}
```

### Creating Service
Services will contain business logic, e.g. CRUD operation in this case.
Create package **service** and create service for every repository. e.g. **PersonService**

Use **@Service** annotation on PersonService. In PersonService, create private object of PersonRepository and use **@Autowired** annotation on it, so spring framework will initilize that object. [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/43ad32aac931df96392dc13dbdeedd16816df34b)

### Creating Controller

Now in the controller package, create **PersonController** that will manage Http requests.
Use **@RestController**, **@RequestMapping(value = "/person")** as we do in controllers. Create an object of PersonService in PersonController and use **@Autowired** annotation on it, so spring framework will manage object creation.

Now create GET, POST, PUT and DELETE methods with **@GetMapping**,**@PostMapping**, **@PutMapping(value = "/{id}")** and **@DeleteMapping(value = "/{id}")**. In the function parameters, use **@PathVariable int id** to get data from URL like localhost/person/1, and if we use **@RequestParam** it would be like localhost/person?id=1 and **@RequestBody Person person** to get data from body. [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/868f7bf954b91d4ef8d5701b6ba27dc39ac8f711)
#### Enabling Cross-Origin
Adding 
```java
@CrossOrigin(origins = "*", allowedHeaders = "*")
``` 
on controller so it can be accessed from anywhere. [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/aa6237082aff953739700658aeaba375f9fc8979)

Adding **@JsonProperty** on entity to will help you to change JSON object name [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/b777ed42d0d20ae0415c530b9a4682396012c11e)

### Using MySQL database instead of H2
Remove H2 Database dependency from  [pom.xml](https://github.com/fawad1997/SpringWebAPI/blob/master/pom.xml) and add [MySQL Connector](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.19)

comment/remove the H2 database configuration from [application.properties](https://github.com/fawad1997/SpringWebAPI/blob/master/src/main/resources/application.properties) file and add MySQL properties as follows: [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/1230fa1723715194d4b5b5feae68705793eb5549)

```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5Dialect
```

### One to Many Relation in Hibernate
#### Creating Entities
lets create two Entities Country and City for oneToMany Relationship.
Create entity **City** with properties **cityId** and **cityName** annotate them with proper annotations like **@Id**, **@GeneratedValue(strategy = GenerationType.AUTO)** and **@Column(name = "cityId")** and also annotate the table with annotations like **@Entity**, **@Data**, **@NoArgsConstructor** and **@AllArgsConstructor**.
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cityId")
    private int cityId;
    private String cityName;
}
```
Now create an entity **Country**, annotate class with annotations **@Entity**, **@Data**, **@NoArgsConstructor** and **@AllArgsConstructor**, and create the following fields [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/17699807e87c42b395db7917fb073c770339be12)

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "countryId")
    private int countryId;
    private String countryName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_fk",referencedColumnName = "countryId")
    private List<City> cities;
}
```
Annotate it with **@OneToMany(cascade = CascadeType.ALL)** and **@JoinColumn(name = "cc_fk",referencedColumnName = "countryId")**. Yhis will create cc_fk column in City table and make it foreign key.

##### Creating Repositories
same as above [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/6c1d0b675fd4d4dd8b7463c41de71f5c61b5540a)
##### Creating Services
same as above [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/362bbd5eb20b8473a36e333143ecd28c3a15e25d)
##### Creating Controllers
same as above [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/72620f3b512d3e5f8ad3a12fcb29846e949c5738)

### Error Handling
Suppose users requests a resource by FindbyId, currently it returns null, instead of null, we will now handle the error and return not found error. For that create a package named **exception** and create a class to handle exception. [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/71dabd4475d16720909d32e4dadf3c0224bdaaaa)
```java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
```
modify getPerson method to throw notfound error
```java
 public Person getPerson(int id){
        Optional<Person> person = personRepository.findById(id);
        if(!person.isPresent()){
            throw new NotFoundException("Person not found!");
        }
        return person.get();
    }
```
### Creating Filters
Create a package **filters** and create filter in it.
Make it **component** of spring framework by adding **@Component** annotation. By default, filter is called on every url pattren. [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/6c14edfd6299cf040d63c8a689c3395d5415de35)
```java
import org.springframework.stereotype.Component;
import javax.servlet.*;
import java.io.IOException;
@Component
public class MyFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter Called");
        chain.doFilter(req, resp);
    }
}

```
To map filter on specific URL pattren, we need to do configuration. create pakage **config** and create class that will map URL pattren as follows: [(referance commit)](https://github.com/fawad1997/SpringWebAPI/commit/0034545758cbe486666a85cc32916a2821681a25)
```java
@Configuration
public class MyFilterConfig {
    @Bean
    public FilterRegistrationBean<MyFilter> registrationBean(){
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/person/*");
        return registrationBean;
    }
}
```

# Spring Security
## Spring Basic Security
To implement spring security, First we need to add **Spring Web Security** from maven repository
- [Spring Boot Web Security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)

Now we should have a table in database to register and authenticate users.
#### Create table for application users
Lets create a table/entity for our application users. e.g. **ApplicationUser**
```java
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
}
```
#### Create repository
Add an extra method **findByUsername** which will help us to find user by their username.
```java
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Integer> {
    ApplicationUser findByUsername(String username);
}
```
#### Cerate Service that will implement UserDetailsService
Now create a service that should implement **UserDetailsService** as follows
```java
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username);
        return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }
}
```
where **User** is from ```import org.springframework.security.core.userdetails.User;``` following import.
