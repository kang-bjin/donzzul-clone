FROM openjdk:17
WORKDIR /app
COPY . /app
RUN ./gradlew build --no-daemon
CMD ["java", "-jar", "build/libs/yourapp.jar"] 
// yourapp.jar 이름은 build/libs/ 아래 실제 jar 파일 이름으로 맞춰야됨