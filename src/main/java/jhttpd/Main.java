package jhttpd;

import static styx.http.server.Server.route;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import styx.http.server.Server;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Path content = Paths.get("content").toAbsolutePath();

    public static void main(String[] args) {
        Arguments arguments = new Arguments("jhttpd", args);
        boolean secure = arguments.getBoolean("secure").orElse(false);
        int port = arguments.getInteger("port").orElse(secure ? 443 : 80);

        logger.info("Starting (port: " + port + ", content: " + content + ").");
        try(Server server = new Server()) {
            server.
                secure(secure).
                port(port).
                routes(
                    route().path("/").toFileSystem(content.resolve("index.html")),
                    route().path("/ping").to((req, res) -> res.write("JHTTPD is running!\n")),
                    route().path("/**").toFileSystem(content)).
                run();
        }
        logger.info("Stopped.");
    }
}
