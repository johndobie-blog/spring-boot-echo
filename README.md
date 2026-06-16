# Spring Boot Echo

![Java 21](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-Kotlin%20DSL-02303A?logo=gradle&logoColor=white)
![Docker Ready](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white)

Lightweight Spring Boot REST API that echoes messages via `GET /echo` and `POST /echo`.

## Contents

- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [API Reference](#api-reference)
- [Build and Test](#build-and-test)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)
- [Development Notes](#development-notes)
- [Project Structure](#project-structure)

## Tech Stack

- Java 21 (Gradle toolchain)
- Spring Boot
- Gradle (Kotlin DSL)
- JUnit 5 for tests

## Prerequisites

- Java 21 installed
- Docker (optional, for container runs)

## Quick Start

Run the app locally:

```bash
./gradlew bootRun
```

The API is available at `http://localhost:8080`.

## API Reference

### `GET /echo`

Returns the query-string message.

Example:

```bash
curl "http://localhost:8080/echo?message=test"
```

Response:

```text
test
```

### `POST /echo`

Accepts JSON body:

```json
{
  "message": "test"
}
```

Validation rules:

- `message` is required
- `message` length must be 1-20 characters

Example:

```bash
curl --location --request POST "http://localhost:8080/echo" \
  --header "Accept: application/json" \
  --header "Content-Type: application/json" \
  --data-raw '{"message":"test"}'
```

Validation failure example (`422 Unprocessable Entity`):

```bash
curl --location --request POST "http://localhost:8080/echo" \
  --header "Accept: application/json" \
  --header "Content-Type: application/json" \
  --data-raw '{"message":"This message is longer than twenty characters"}'
```

## Build and Test

Run tests:

```bash
./gradlew test
```

Build executable jar:

```bash
./gradlew bootJar
```

## Docker

Build image:

```bash
docker image build . --tag "johndobie/spring-boot-echo"
```

Run container:

```bash
docker run --rm -p 8080:8080 -t "johndobie/spring-boot-echo"
```

## Troubleshooting

### Port `8080` already in use

```bash
lsof -ti tcp:8080 | xargs kill
```

### Java version mismatch

Check your local Java version:

```bash
java -version
```

This project targets Java 21 and is configured with a Gradle toolchain in `build.gradle.kts`.

### Build warnings for Gradle 9 compatibility

Show all deprecation warnings:

```bash
./gradlew test --warning-mode all
```

### Docker image fails to start

- Ensure the boot jar exists by running `./gradlew bootJar`
- Rebuild the image after any code changes
- Verify no other service is bound to `8080`

## Development Notes

- Use `./gradlew clean test` before opening a PR.
- Keep the API contract for `/echo` stable unless intentionally versioning.
- Prefer `build.gradle.kts` and `settings.gradle.kts` for all build configuration updates.
- Regenerate docs examples if request/response behavior changes.
- Keep Docker and local run instructions in sync after build changes.

## Project Structure

- `src/main/java` - application source
- `src/test/java` - tests
- `build.gradle.kts` - Gradle build configuration
- `Dockerfile` - container build definition

