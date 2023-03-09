#start with a base image containing java runtime
FROM openjdk:8 as build
#EXPOSE 8080
#ADD target/book-my-show.jar book-my-show.jar
# Add the application's jar to the container
COPY target/book-my-show.jar book-my-show.jar
#Execute the appliation
ENTRYPOINT ["java","-jar","/book-my-show.jar"]

