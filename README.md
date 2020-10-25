This project is created for recruitment purposes.

You can load transactions and rates csv files and display them in a table.

Example data can be found in mock-data folder

## To Run in development

###frontend
```bash
$ cd frontend/src/main/angular
$ npm i
$ npm run start
```
###backend
```bash
$ cd backend
$ mvn clean install
$ java -jar target/backend-1.0.2-SNAPSHOT.jar
```

## Built With

* [Maven](https://maven.apache.org/)
* [Spring Boot 2.1.5](https://start.spring.io/)
* [JOOQ](https://www.jooq.org/)
* [H2 Database](https://www.h2database.com/html/main.html)
* [Angular 10](https://angular.io/)
* [NgZorro](https://ng.ant.design/)
* [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin) ([npm 6.13.7](https://github.com/npm/cli), [Node.js v12.14.1](https://nodejs.org/dist/latest-v12.x/docs/api/))

## License

This project is licensed under the Unlicense - see the [license details](https://choosealicense.com/licenses/unlicense/).
