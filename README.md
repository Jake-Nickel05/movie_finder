# Movie Finder

An Android app for discovering movies, backed by a Java Spring Boot REST API. Users can browse popular movies, run advanced searches, watch trailers, bookmark favorites, comment on movies and see live view counts. Built as a four-person team project for COM S 309 (Software Development Practices) at Iowa State University, Fall 2024.

## Features

- **Movie search and discovery** using data from the [TMDB API](https://www.themoviedb.org/documentation/api): popular movies, title search, recommendations, similar movies and trailers
- **Advanced search** with filters for genre, cast, keywords, release date, rating and runtime
- **User accounts** with sign-up, login and password reset
- **Role-based access** for regular users, managers and admins
- **Bookmarks and comments** saved per user
- **Real-time features over WebSockets**: live movie view counts and admin direct messages

## Tech stack

| Part | Technologies |
|---|---|
| Backend | Java, Spring Boot, Spring Data JPA (Hibernate), MySQL, WebSockets, Maven, Swagger/OpenAPI docs |
| Android app | Java, Android SDK 34, Volley, Picasso, WebSocket client |
| Testing | JUnit, REST Assured (backend system tests), Espresso (Android system tests) |
| Tools | Git, GitLab CI/CD |

## Project structure

```
backend/   Spring Boot REST API and WebSocket server
android/   Android client app
```

## My contributions

<!-- Fill this in: what you personally built. Examples: -->
<!-- - Designed and built the ___ REST endpoints and database entities -->
<!-- - Implemented the ___ WebSocket feature -->
<!-- - Wrote system tests for ___ -->

## Running it locally

### Backend

Requires Java 17+, Maven and a MySQL database.

1. Get a free API key from [TMDB](https://www.themoviedb.org/settings/api).
2. Set these environment variables:
   ```
   TMDB_API_KEY=your_tmdb_key
   DB_URL=jdbc:mysql://localhost:3306/moviefinder?useSSL=false
   DB_USERNAME=your_mysql_user
   DB_PASSWORD=your_mysql_password
   ```
3. From the `backend` folder, run:
   ```
   mvn spring-boot:run
   ```
   The API runs on port 8080, with interactive API docs at `/docs/swagger-ui-custom.html`.

### Android app

Open the `android` folder in Android Studio and run it on an emulator or device. The app points at the course server address in its source files; change those URLs to `http://10.0.2.2:8080` to use a backend running on your own machine from the emulator.

## Team

Built with three teammates for COM S 309 at Iowa State University.
