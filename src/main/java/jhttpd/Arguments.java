package jhttpd;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class Arguments {

    private final String prefix;
    private final List<String> args;

    Arguments(String prefix, String[] args) {
        this.prefix = Objects.requireNonNull(prefix);
        this.args = Arrays.asList(args);
    }

    Optional<Boolean> getBoolean(String name) {
        return getString(name).map(Boolean::parseBoolean);
    }

    Optional<Integer> getInteger(String name) {
        return getString(name).map(Integer::parseInt);
    }

    Optional<String> getString(String name) {
        for(String arg : args) {
            if(arg.startsWith("-" + name + "=")) {
                return Optional.of(arg.substring(name.length() + 2));
            }
        }
        name = prefix + "-" + name;
        String property = System.getProperty(name.replace("-", "."));
        if(property != null) {
            return Optional.of(property);
        }
        String env = System.getenv(name.replace("-", "_").toUpperCase());
        if(env != null) {
            return Optional.of(env);
        }
        return Optional.empty();
    }

    Optional<Path> getPath(String name) {
        return getString(name).map(Paths::get);
    }
}
