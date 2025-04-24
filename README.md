# Knowledge Base

Knowledge Base is a Spring Boot application designed to manage an extensive repository of cultural knowledge.
It serves as a centralized platform for collecting and organizing vast collections of cultural data,
including annual genre-based rankings and ratings of top musicians, writers, artists, and their respective works.
The application provides dynamic list generation, detailed metadata management, and user-friendly access to
cultural insights.

The application is deployed on a server and can be accessed at the URL: [dmitryshundrik.com](https://dmitryshundrik.com)

## Project Overview

### Features

- **Dynamic List Generation**: Create and manage ranked lists of cultural works (e.g., music albums, books, artworks) based 
on user-defined criteria or annual rankings.
- **Metadata Management**: Store and organize detailed metadata for cultural entities, including artists, genres, and works, 
with support for complex relationships.
- **Cultural Data Access**: Provide a user-friendly interface for browsing, searching, and retrieving cultural information 
with advanced filtering and sorting.
- **External API Integration**: Fetch supplementary data (e.g., artist biographies, work descriptions) from external sources 
using WebClient.
- **Custom Reporting**: Generate aggregated reports with SQL-based analytics (e.g., top genres by year, artist influence 
trends).

### Tech Stack

- **Language**: Java 17 (leveraging BigDecimal for precision, enums for type safety, Stream API for data processing)
- **Framework**: Spring Boot (Web, Data JPA for database operations)
- **Database**: PostgreSQL for robust relational data storage
- **ORM**: Spring Data JPA with custom SQL queries (JOINs, subqueries, aggregations)
- **Migration Tool**: Liquibase for database schema versioning and migrations
- **DTO Mapping**: MapStruct for efficient object mapping between entities and DTOs
- **Build Tool**: Maven for dependency management and build automation
- **Testing**: JUnit and Mockito for unit and integration testing
- **Other Libraries**: Lombok for boilerplate reduction, SLF4J for logging

### Contact

For questions or feedback, please reach out to dmitryshundrik@gmail.com
   












