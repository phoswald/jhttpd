# jhttpd
A minimalistic, docker friendly HTTP(S) server

## Running

    mvn clean verify
    java -cp target/jhttpd.jar:"target/lib/*" jhttpd.Main
    java -cp target/jhttpd.jar:"target/lib/*" -Djhttpd.secure=true -Djhttpd.port=8443 jhttpd.Main

## Docker

    mvn clean verify
    docker build -t jhttpd .
    docker run -d --name my-jhttpd -p 8080:80 -v ~/web:/usr/local/jhttpd/content jhttpd
    docker run -d --name my-jhttpd2 -p 8443:443 -v ~/web:/usr/local/jhttpd/content -e "JHTTPD_SECURE=true" jhttpd

## Configuration

Arguments can be passed as command line arguments, JVM system properties, and environment variables:

    java jhttpd.Main -secure=true
    java -Djhttpd.secure=true jhttpd.Main
    export JHTTPD_SECURE=true && java jhttpd.Main

Valid arguments are:


- -secure=*true|false* to use HTTPS or HTTP (default: false, i.e. HTTP)
- -domain=*qualified domain name* defines the host name (default: localhost, used for self-signed SSL certificate)
- -port=*number* to select the TCP port (default: 80 for HTTP, 443 for HTTPS)
- -content=*path* defines the file system path to be served (default: content)
