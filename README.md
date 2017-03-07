# jhttpd
A minimalistic HTTP and HTTPS server

## Running

    mvn clean verify
    java -cp target/jhttpd.jar:"target/lib/*" jhttpd.Main
    java -cp target/jhttpd.jar:"target/lib/*" -Djhttpd.secure=true -Djhttpd.port=8443 jhttpd.Main

## Docker

    mvn clean verify
    docker build -t jhttpd .
    docker run -d --name my-jhttpd -p 8080:80 -v ~/web:/usr/local/jhttpd/content jhttpd
    docker run -d --name my-jhttpd2 -p 8443:443 -v ~/web:/usr/local/jhttpd/content -e "JHTTPD_SECURE=true" jhttpd
