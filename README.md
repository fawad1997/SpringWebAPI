# SpringWebAPI
Create a package for controllers in src>main>java>[your pakage name]/controller

Add a controller to display hello world message. **@RestController** makes it restfull controller and **@RequestMapping(value = "/hello")** defines URL mapping

## CRUD with H2 Database
to use database we need few dependencies to be installed
Go to <a href="https://mvnrepository.com/">mavenrepository</a>
and search for the following dependencies and add it to your pom.xml _dependencies_ section
Please donot include test scope as we will not be doing testing at that stage
<ul><li><a href="https://mvnrepository.com/artifact/org.projectlombok/lombok"> Lombok</a> </li>
<li><a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa">Data JPA Starter </a></li>
<li><a href="https://mvnrepository.com/artifact/com.h2database/h2"> H2 Database</a></li>
</ul>
Now you have added a database named H2 database(in-memory db), you need to specify database name too
<ol>
<li>Open <b>application.properties</b> in resources folder </li>
<li>specify database name as follows</li>
</ol>
<code>
spring.datasource.url=jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE
</code><br/>
<code>
spring.jpa.hibernate.ddl-auto=update
</code>
<h4>Creating Models/Tables/Entities</h4>
Create a package <b>entity</b> where you will create Entity classes
e.g we are creating <i>Person</i> entity 

Use **@Entity** annotation to make it entity, **@Data** to make setter getters, **@NoArgsConstructor** to create no argument constructor, **@AllArgsConstructor** to make argument constructor. **@Id** to make it primary key, **@GeneratedValue(strategy = GenerationType.AUTO)** for autoincrement.
Now your table will be created once the application starts.

<h4>Creating Repository</h4>
Now create a repository for every entity, create package named <b>repository</b> and create interface,, e.g. PersonRepository that will extend <b>JpaRepository<Person,Integer></b> and use <b>@Repository</b> annotation on it.

<h4>Creating Service</h4>
Services will contain business logic, e.g. CRUD operation in this case.
Create package <b>service</b> and create service for every repository. e.g. <b>PersonService</b>

Use **@Service** annotation on PersonService. In PersonService, create private object of PersonRepository and use **@Autowired** annotation on it, so spring framework will initilize that object.

<h4>Creating Controller</h4>

Now in the controller package, create **PersonController** that will manage Http requests.
Use **@RestController**, **@RequestMapping(value = "/person")** as we do in controllers. Create an object of PersonService in PersonController and use **@Autowired** annotation on it, so spring framework will manage object creation.

Now create GET, POST, PUT and DELETE methods with **@GetMapping**,**@PostMapping**, **@PutMapping(value = "/{id}")** and **@DeleteMapping(value = "/{id}")**. In the function parameters, use **@PathVariable int id** to get data from URL and **@RequestBody Person person** to get data from body.

Adding <b>@CrossOrigin(origins = "*", allowedHeaders = "*")</b> on controller so it can be accessed from anywhere.

Adding **@JsonProperty** on entity to get exact case as we want 

### Using MySQL database instead of H2
Remove H2 Database dependency from **pom.xml** and add <a href="https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.19">MySQL Connector</a>

comment/remove the H2 database configuration from **application.properties** file and add MySQL properties as follows:

<code>spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver</code><br/>
<code>spring.datasource.url=jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC</code><br/>
<code>spring.datasource.username=root</code><br/>
<code>spring.datasource.password=</code><br/>
<code>spring.jpa.show-sql=true</code><br/>
<code>spring.jpa.hibernate.ddl-auto=update</code><br/>
<code>spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5Dialect</code><br/>

### One to Many Relation in Hibernate
##### Creating Entities
lets create two Entities Country and City for oneToMany Relationship.
Create entity **City** with properties **cityId** and **cityName** annotate them with proper annotations like **@Id**, **@GeneratedValue(strategy = GenerationType.AUTO)** and **@Column(name = "cityId")** and also annotate the table with annotations like **@Entity**, **@Data**, **@NoArgsConstructor** and **@AllArgsConstructor**.

Now create an entity **Country**, annotate class with annotations **@Entity**, **@Data**, **@NoArgsConstructor** and **@AllArgsConstructor**, and create the follond fields

<code>countryId</code> annotate it with **@Id**, **@GeneratedValue(strategy = GenerationType.AUTO)** and **@Column(name = "countryId")**

<code>countryName</code>

<code>List<.City> cities</code> and annotate it with **@OneToMany(cascade = CascadeType.ALL)** and **@JoinColumn(name = "cc_fk",referencedColumnName = "countryId")**. Yhis will create cc_fk column in City table and make it foreign key.


