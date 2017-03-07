FROM openjdk:8-jre-alpine
COPY target/jhttpd.jar /usr/local/jhttpd/
COPY target/lib        /usr/local/jhttpd/lib
RUN mkdir /usr/local/jhttpd/content
EXPOSE 8080
WORKDIR /usr/local/jhttpd
CMD exec java -cp jhttpd.jar:"lib/*" jhttpd.Main
