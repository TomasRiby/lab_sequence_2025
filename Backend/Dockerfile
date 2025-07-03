# syntax=docker/dockerfile:1
###############################################################################
# ── 1. Build stage ───────────────────────────────────────────────────────────
###############################################################################
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /build

# 1-a) Copy wrapper + Maven metadata first so we get layer-cache hits
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml .

# 1-b) Pre-download deps into the build cache
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -q dependency:go-offline -DskipTests

# 1-c) Copy the source and build (creates target/quarkus-app/)
COPY src src/
RUN ./mvnw -q package -DskipTests

###############################################################################
# ── 2. Runtime stage ─────────────────────────────────────────────────────────
###############################################################################
#FROM eclipse-temurin:17-jre-jammy AS run
#ARG UID=10001
#RUN adduser --disabled-password --uid "$UID" appuser
#USER appuser
#
#WORKDIR /work
## Copy the **entire** fast-jar directory (quarkus-run.jar + libs etc.)
#COPY --from=build /build/target/quarkus-app/ .
#
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]

###############################################################################
# ── 3. DEV  (Maven) ─────────────────────────────────
###############################################################################

FROM maven:3.9-eclipse-temurin-17 AS dev
WORKDIR /workspace

COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml .
COPY src src/

RUN --mount=type=cache,target=/root/.m2 mvn -q dependency:go-offline

EXPOSE 8080
CMD ["mvn","quarkus:dev","-Dquarkus.http.host=0.0.0.0"]
