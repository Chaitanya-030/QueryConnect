## Overview
QueryConnect is a Java-based application that translates Keyword Query Language (KQL) into SPARQL queries and executes them on a DBpedia endpoint. Built with Spring Boot and Apache Jena ARQ, this application provides an intuitive interface for querying RDF data sources.

## Features
1. KQL to SPARQL Translation: Converts user-submitted KQL queries into SPARQL queries. <br/>
2. SPARQL Execution: Executes SPARQL queries on a DBpedia endpoint and returns the results. <br/>
3. User Interface: Web-based interface for entering KQL queries and displaying results.

## Technologies Used
1. Java: Core programming language. <br/>
2. Spring Boot: Framework for building the application. <br/>
3. Apache Jena ARQ: API for query translation and execution. <br/>
4. Apache Tomcat: Server for hosting the web interface.

## Installation
1. Clone the repository.
2. Navigate to the project directory.
3. Build the project using Maven.
4. Run the application.

```
git clone <repository-url>
cd QueryConnect
mvn clean install
mvn spring-boot:run
```
