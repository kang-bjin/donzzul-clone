FROM openjdk:17-alpine
WORKDIR /app

# xargs 포함된 유틸 설치
RUN apk update && apk add findutils

# gradle 소스 복사
COPY . .

# 빌드
RUN ./gradlew build --no-daemon

# 실행
CMD ["java", "-jar", "build/libs/backend.jar"]
