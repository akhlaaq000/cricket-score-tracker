# Live Cricket Scores - Minimal Spring Boot Skeleton

This repository contains a minimal Spring Boot Java 17 skeleton implementing the structure from the design doc `finallive_cricket_scores_system_full_hld_lld_design_document(1).md`.

What is included:
- Maven `pom.xml` with Spring Boot, Spring Data JPA, Spring Security, and H2 for local testing
- Base packages: controllers, services, models, dtos, repositories, config
- Minimal `MatchController` and `MatchService` skeletons (see `src/main/java/com/example/cricket`)

How to run locally:

1. Build:

```bash
mvn -q -DskipTests package
```

2. Run:

```bash
java -jar target/cricket-0.0.1-SNAPSHOT.jar
```

Notes and next steps:
- This is a starting point. Implement JWT-based authentication, external API integration, caching, and analytics as next tasks.
- To use a real DB, update `application.properties` or pass standard Spring datasource env vars.
