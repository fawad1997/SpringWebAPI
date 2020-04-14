# SpringWebAPI
Create a package for controllers in src>main>java>[your pakage name]/controller

Add a controller to display hello world message. **@RestController** makes it restfull controller and **@RequestMapping(value = "/hello")** defines URL mapping

## CRUD with H2 Database
to use database we need few dependencies to be installed
Go to mavenrepository https://mvnrepository.com/
and search for the following dependencies and add it to your pom.xml _dependencies_ section
Please donot include test scope as we will not be doing testing at that stage
<li><a href="https://mvnrepository.com/artifact/org.projectlombok/lombok"> Lombok</a> </li>
<li><a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa">Data JPA Starter </a></li>
<li><a href="https://mvnrepository.com/artifact/com.h2database/h2"> H2 Database</a></li>
Now you have added a database named H2 database(in-memory db), you need to specify database name too
<ol>
<li>Open <b>application.properties</b> in resources folder </li>
<li>specify database name as follows</li>
</ol>
<code>
spring.datasource.url=jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE
</code>
<code>
spring.jpa.hibernate.ddl-auto=update
</code>