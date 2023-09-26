FROM openjdk:11 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:11
WORKDIR account
COPY --from=build target/*.jar account.jar
ENTRYPOINT ["java","-jar","account.jar"]
#docker da image oluşturma komutu >docker build . -t account:1.0
#docker da ayağa kaldırmak için >docker run --name account -d -p 9090:8080 account:1.0
#docker ps -a ->bu komutla çalışan containerleri görürsünüz
#docker container rm containerId -> container durdurulur olmazsa docker container rm -f containerId dene force ile drdurmaya zorla!