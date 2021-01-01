FROM openjdk:8
WORKDIR .
COPY . /tmp/SimpleChat-TCP/
RUN chmod +x /tmp/SimpleChat-TCP/client.sh && chmod +x /tmp/SimpleChat-TCP/server.sh
CMD java -cp tmp/SimpleChat-TCP/target/SimpleChat-TCP-1.0.jar app.Server
CMD java -cp tmp/SimpleChat-TCP/target/SimpleChat-TCP-1.0.jar app.Client