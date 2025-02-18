# Customer CRUD Application

## Pre-requisites
1. Install MySQL and run in post 3306
2. Reference - https://www.datacamp.com/tutorial/set-up-and-configure-mysql-in-docker
```
$ docker pull mysql:9.2
$ docker run --name customers-mysql -e MYSQL_ROOT_PASSWORD=password -d mysql
$ docker exec -it customers-mysql bash
$ mysql -u root -p
$ CREATE DATABASE customer_db;
```

## Run the application
Make sure MySQL container is up and running.
```
$ ./gradlew build
$ java -jar build/libs/customers-1.0.0.jar
```

