# [Hibernate Envers](http://hibernate.org/orm/envers/) History

## Description

Makes a servlet available on your web project that displays a useful web page with information provided by Hibernate Envers.
The objective of this project is to build a simple alternative to [django-simple-history](https://github.com/treyhunner/django-simple-history) 
in Java.

The project works by displaying all entities of your web project annoted by @Audited annotation of Hibernate Envers. It shows all 
entities available in your database and the revision histories of each entity. You may also perform a revert operation to 
any revision of your selected entity.

The front end was developed with Angular and backend with Jax-rs with Jersey.

Example: Showing all registers of entity Student:
<h1 align="center">
<a href="https://github.com/dudevictor/hibernate-envers-history"><img src="https://cdn.rawgit.com/dudevictor/hibernate-envers-history/master/media/registers.png" alt="Showing all registers of entity Student" ></a></h1>
			
Detailing selected register:			
<h1 align="center">
<a href="https://github.com/dudevictor/hibernate-envers-history">
				<img src="https://cdn.rawgit.com/dudevictor/hibernate-envers-history/master/media/register.png" alt="Detailing selected register" >
			</a></h1>
			
Reverting entity to revision 2
<h1 align="center">
<a href="https://github.com/dudevictor/hibernate-envers-history">
				<img src="https://cdn.rawgit.com/dudevictor/hibernate-envers-history/master/media/revertExample.png" alt="Reverting entity to revision 2" >
			</a></h1>

## Installation & Usage

Put tthe following configuration on your web.xml:
```
<servlet>
		<servlet-name>jersey-serlvet</servlet-name>
		<servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
		<init-param>
			<param-name>jersey.config.server.provider.packages</param-name>
			<param-value>br.com.logique.hibernatehistory</param-value>
		</init-param>

		<init-param>
			<param-name>hibernate.envers.history.username</param-name>
			<param-value>admin</param-value> <!-- Sets the username used to make login -->
		</init-param>

		<init-param>
			<param-name>hibernate.envers.history.password</param-name>
			<param-value>admin</param-value> <!-- Sets the password used to make login -->
		</init-param>

		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>jersey-serlvet</servlet-name>
		<url-pattern>/audit/*</url-pattern> <!-- Sets the route that makes the web page available. It will be available like: http://<domain>:<port>/<app-context>/audit -->
	</servlet-mapping>
  
```

Adds the .jar dependency in your pom.xml (**Not available yet**):
```
<dependency>
  <groupId>br.com.logique</groupId>
  <artifactId>hibernate-envers-history</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```


