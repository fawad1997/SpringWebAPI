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
- [Many to Many Relation in Hibernate](#many-to-many-relation-in-hibernate)
- [Error Handling](#error-handling)
- [JPA Hibernate Validations](#jpa-hibernate-validations)
- [Creating Filters](#creating-filters)
- [Spring Security](#spring-security)
    - [Spring Basic Security](#spring-basic-security)
        - [Create table for application users](#create-table-for-application-users)
        - [Create repository](#create-repository)
        - [Create Service that will implement UserDetailsService](#create-service-that-will-implement-userdetailsservice)
        - [Create Security Configuration File](#create-security-configuration-file)
- [Jwt Authentication](#jwt-authentication)
    - [Adding Dependencies](#adding-dependencies)
    - [Create JwtUtilService](#create-jwtutilservice)
    - [Generate Token](#generate-token)
    - [Allow Anonymous request](#allow-anonymous-request)
    - [Create filter to check token](#create-filter-to-check-token)
    - [Register User](#register-user)

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

### Many to Many Relation in Hibernate
Lets take the example of two entities **Student** and **Course**, One student can be enrolled in multiple courses, similarly, Each course contains multiple students. If you remember database normalization rules, we need to break the entity to seperate entity for Many to Many relation.

One ony below table, use **@JoinTable** annotation to specify 3rd table name, column to join from current table, and column from the other table. 
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String name;
    private String regNo;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_courses",
            joinColumns = {@JoinColumn(name = "studentId")},
            inverseJoinColumns = {@JoinColumn(name = "courseId")})
    private List<Course> courses;
}
```
and
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String courseTitle;
    private String courseCode;
    @ManyToMany
    private List<Student> students;
}
```
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
### JPA Hibernate Validations
To customize table design, we can use different annotatons on top of each field.

- **@Id** makes it Primary Key.
- **@GeneratedValue(strategy = GenerationType.IDENTITY)** make it autoincrement by 1.
- **@JsonProperty(value = "ID")** make sures it should be **ID** in JSON format instead of **id**
- **@NotNull(message = "Name cann't be null")** makes the field non nullable.
- **@Size(min = 2,max = 100, message = "Name must be minimum 2 characters and maximum 100 characters long")** validates the size of the field.
- **@Email** is used to validate email.
- **@Min(8)** and **@Max(110)** says that number should be between 8-110.
- **@Pattern(regexp = "^\\(?(\\d{5})\\)?[-]?(\\d{7})[-]?(\\d{1})$",message = "CNIC sholud be in format xxxxx-xxxxxxx-x")** is used for regular expression.
- **@Past** makes sure that date should be from past, mnot future.
- **@JsonFormat(pattern = "yyyy-MM-dd")** make sures JSON data should be in this format.

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "ID")
    private int id;
    @JsonProperty(value = "Name")
    @NotNull(message = "Name cann't be null")
    @Size(min = 2,max = 100, message = "Name must be minimum 2 characters and maximum 100 characters long")
    private String name;
    @Email
    private String email;
    @JsonProperty(value = "Age")
    @Min(8)
    @Max(110)
    private int age;
    @JsonProperty(value = "Height")
    private double height;
    @JsonProperty(value = "CNIC")
    @Pattern(regexp = "^\\(?(\\d{5})\\)?[-]?(\\d{7})[-]?(\\d{1})$",message = "CNIC sholud be in format xxxxx-xxxxxxx-x")
    private String cnic;
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate doB;
}
```
After adding these annotations, our table will be created with those restrictions. But if we insert invalid data through JSON, our application will throw exception or maybe crash. We need to validate these properties in controller to avoid exceptions.
#### Handling Validations in Controller
Use **@Valid** to check for validations, and also inject BindingResult in it which will help us to catch errors, If invalid, then return errors, else, return created object. Simplarly check for validations in PutMapping function too.
```java
@PostMapping
public ResponseEntity<?> addPerson(@Valid @RequestBody Person person, BindingResult result){
    if(result.hasErrors()){
        Map<String,String> errors = new HashMap<>();
        for(FieldError error:result.getFieldErrors()){
            errors.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity<Map<String,String>>(errors, HttpStatus.BAD_REQUEST);
    }
    Person p = personService.addPerson(person);
    return new ResponseEntity<Person>(p,HttpStatus.CREATED);
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
    @NotBlank(message = "username cann't be blank")
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
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
#### Create Service that will implement UserDetailsService
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
#### Create Security Configuration File
Now create a configure class that will extend **WebSecurityConfigurerAdapter** and tell spring security to use our own userdetails service.
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
Now application will use our own customized security
#### Add data to database on application start
(Optional) Suppose we want to new user data into database when the application starts we can create new method in main class and use **@PostConstruct** annotation to bind it with application start.
```java
@Autowired
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @PostConstruct
    public void seedUser() {
        if (userRepository.findByUsername("test") == null) {
            String encodedPassword = passwordEncoder.encode("12345");
            ApplicationUser user = new ApplicationUser(1, "test", encodedPassword);
            userRepository.save(user);
        }
    }
``` 
## Jwt Authentication
[JSON Web Tokens](https://jwt.io/) are an open, industry standard RFC 7519 method for representing claims securely between two parties.
Its on one of the best way of authentication of modern time.
![](/gitimages/jwt.jpg)
#### Adding Dependencies
To include Jwt in your project, you need to include the following dependencies:
- [JSON Web Token](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt/0.9.1)
- [JAXB API](https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api/2.3.1)

you need to include [JAXB API](https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api/2.3.1) if you are using Java 9 or above.

#### Create JwtUtilService
Now create a JwtUtilService that will contains business logic needed by Jwt. Copy it as given below
```java
@Service
public class JwtUtilService {
    private final String secret = "fawad";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
```

#### Generate Token
Now lets create a controller that will be responsible for creating **User** account and generate **Jwt Token**.

Before creating token you need to add one more method in your **SecurityConfig** class for AuthenticationManager, as we will be using it in our controller.
```java
@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
``` 
add it in **SecurityConfig** class.

Now lets create a controller to generate token
```java
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtilService jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody ApplicationUser userCredentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCredentials.getUsername(),userCredentials.getPassword())
            );
        }catch (Exception e){ throw new Exception("Invalid Credentials");}
        return jwtUtil.generateToken(userCredentials.getUsername());
    }
}
```
#### Allow Anonymous request
As authorization is applied to every page, but we want that there should be no authorization on **AccountController** so we can register or login user, to do that, you need to modify **SecurityConfig** file.
```java
  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/account/**").permitAll()
                .anyRequest().authenticated();
    }
```

#### Create filter to check token
Now lets create a filter that will extend **OncePerRequestFilter** to verify token.
```java
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtilService jwtUtil;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYXdhZDE5OTciLCJleHAiOjE1ODcxNTA2NjgsImlhdCI6MTU4NzE0ODg2OH0.OEvx_a1nTW2vzH7ofuDXJHL8By_32_D3OIfycBoXykY
        String token = null;
        String username = null;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.loadUserByUsername(username);
            if(jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
```
and in the Security config, register filter and stateless session. Inject **JwtFilter** using **@Autowired** and update the configure method.
```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/account/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    } 
```
#### Register User
Create a method in **UserService** that will contain business logic of user creation.
```java
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public Boolean registerUser(ApplicationUser user){
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
        return true;
    }
```

Now create a method in controller to register user.
```java
    @PostMapping("/register")
    public String register(@RequestBody ApplicationUser user){
        if(userService.registerUser(user)){
            return "User Created";
        }
        return "User Creation Failed!";
    }
```
