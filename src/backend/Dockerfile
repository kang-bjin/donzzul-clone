# 1) Builder: Gradle 빌드
FROM openjdk:17-alpine AS builder
WORKDIR /workspace

# xargs 등 유틸 설치
RUN apk update && apk add --no-cache findutils bash

# 1-1) Gradle Wrapper 스크립트 복사
COPY gradlew gradlew.bat ./
# 1-2) Gradle Wrapper jar·properties 복사
COPY gradle/ gradle/
# 1-3) 빌드스크립트 복사
COPY build.gradle settings.gradle ./

# 실행 권한 부여
RUN chmod +x gradlew

# 1-4) 소스코드 복사
COPY src/ src/

# 1-5) JAR 빌드 (테스트 제외)
RUN ./gradlew build --no-daemon -x test

# 2) Runtime: 오직 JAR 실행
FROM openjdk:17-alpine
WORKDIR /app

# 빌더에서 만든 JAR만 복사
COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
