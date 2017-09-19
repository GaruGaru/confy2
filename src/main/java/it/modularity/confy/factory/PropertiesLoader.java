package it.modularity.confy.factory;

import it.modularity.confy.Confy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

public class PropertiesLoader {

    public static Optional<Properties> load(String propFile) {

        if (!propFile.endsWith(".properties"))
            propFile += ".properties";

        Optional<Properties> local = fromLocal(propFile);
        Optional<Properties> classPath = fromClassPath(propFile);

        return classPath.isPresent() ? classPath : local;
    }

    public static Optional<Properties> fromLocal(String name) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(name));
            return Optional.of(properties);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<Properties> fromClassPath(String name) {
        try {
            Properties properties = new Properties();
            InputStream res = ClassLoader.getSystemResourceAsStream(name);
            if (res == null)
                return Optional.empty();
            properties.load(res);
            return Optional.of(properties);
        } catch (IOException ex) {
            return Optional.empty();
        }
    }


}
