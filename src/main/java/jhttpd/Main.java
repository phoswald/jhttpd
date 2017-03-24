package jhttpd;

import static styx.http.server.Server.route;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import styx.daemon.utils.Arguments;
import styx.daemon.utils.Daemon;
import styx.daemon.utils.LogConfig;
import styx.http.server.Server;

public class Main {

    static {
        LogConfig.activate();
    }

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Daemon.main(args, Main::run);
    }

    private static void run(String[] args, CountDownLatch latch) throws InterruptedException {
        Arguments arguments = new Arguments("jhttpd", args);
        boolean secure = arguments.getBoolean("secure").orElse(false);
        String domain = arguments.getString("domain").orElse("localhost");
        int port = arguments.getInteger("port").orElse(secure ? 443 : 80);
        Path content = arguments.getPath("content").orElse(Paths.get("content")).toAbsolutePath();
//      String home = arguments.getString("home").orElse(".");

        logger.info("Starting (port: " + port + ", content: " + content + ").");

        try(Server server = new Server()) {
            server.secure(secure, domain).
            port(port).
            routes(
//              route().path("/").toFileSystem(content.resolve(home)),
                route().path("/ping").to((req, res) -> res.write("JHTTPD is running!\n")),
                route().path("/**").toFileSystem(content)).
            run(latch);
        }

        logger.info("Stopped.");
    }
}
