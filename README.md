### An example of how to build REST services using Spring Boot.

#### To Run
```
mvn clean install spring-boot:run
```

#### Docker Build
docker image build . --tag="johndobie/spring-boot-example"

#### Run the image locally
docker run -p 8080:8080 -t "johndobie/spring-boot-example"    

### Run In Docker Locally
Build the image
``` 
 Sending build context to Docker daemon  19.41MB
 Step 1/7 : FROM openjdk:8u212-jdk-alpine
  ---> a3562aa0b991
 Step 2/7 : LABEL maintainer="john@dobie.email"
  ---> Using cache
  ---> 44b0a1cd1876
 Step 3/7 : VOLUME /tmp
  ---> Using cachedocker
  
  ---> 487893eed320
 Step 4/7 : EXPOSE 8080
  ---> Using cache
  ---> 77c955bfd0dc
 Step 5/7 : ARG JAR_FILE=target/*.jar
  ---> Using cache
  ---> f27a9ac993d5
 Step 6/7 : ADD ${JAR_FILE} echo.jar
  ---> Using cache
  ---> 61704f9e8aa5
 Step 7/7 : ENTRYPOINT ["java","-jar","/echo.jar"]
  ---> Using cache
  ---> 52b221836c76
 Successfully built 52b221836c76
 Successfully tagged johndobie/spring-boot-echo:latest
```

Run the image locally
```
 docker run -p 8080:8080 -t "johndobie/spring-boot-echo"    
```

## Push to Dockerhub
Login to DockerHub
```
>docker login -u johndobie

Password:
Login Succeeded
```

Push to DockerHub
```
>docker push johndobie/spring-boot-echo
The push refers to repository [docker.io/johndobie/spring-boot-echo]
The push refers to repository [docker.io/johndobie/spring-boot-echo]
7a1572ef2719: Pushed
ceaf9e1ebef5: Pushed
9b9b7f3d56a0: Pushed
f1b5933fe4b5: Pushed
latest: digest: sha256:8ff6f8504247393a78c89c40a76fcacf83920e860b5db7620611a6d365e507c2 size: 1159
```
