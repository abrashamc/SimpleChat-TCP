FROM openjdk:8
MAINTAINER achowdhury
WORKDIR /target
RUN chmod +x ./client.sh
RUN chmod +x ./server.sh
COPY client.sh /target/
COPY server.sh /target/
COPY Dockerfile /target/
RUN java -cp target/SimpleChat-TCP-1.0.jar app.Server
RUN java -cp target/SimpleChat-TCP-1.0.jar app.Clients