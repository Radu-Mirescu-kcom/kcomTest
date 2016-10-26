package ro.rinf.kcom.devtest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PropertyRepository implements Consumer<Properties>, Supplier<Properties> {
    private static final String DEFAULT_RESOURCE_FILE = "coin-inventory.properties";
    private String resourceFile = DEFAULT_RESOURCE_FILE;

    @Override
    public void accept(Properties properties) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(resourceFile);
            synchronized (resourceFile ) {
                try( PrintStream printStream = new PrintStream(new File(url.toURI().getPath()),"UTF-8")){
                    Enumeration pNames = properties.propertyNames();
                    while(pNames.hasMoreElements()) {
                        String propertyName = pNames.nextElement().toString();
                        printStream.print(String.format("%s=%s%n",propertyName,properties.get(propertyName).toString()));
                    }
                }
            }
        } catch( IOException | URISyntaxException ex ) {
            throw new UnexpectedException(String.format("Error at saving %s",resourceFile),ex);
        }
    }

    @Override
    public Consumer<Properties> andThen(Consumer<? super Properties> after) {
        throw new UnexpectedException("The method andThen is not implemented in PropertyRepository!");
    }

    @Override
    public Properties get() {
        final Properties properties = new Properties();
        synchronized (resourceFile ) {
            try (final InputStream stream =
                     this.getClass().getClassLoader().getResourceAsStream(resourceFile)) {
                properties.load(stream);
                return properties;
            } catch(IOException ioEx) {
                throw new UnexpectedException(
                    String.format("Unexpected exception at accessing the properties file %s!",resourceFile),
                    ioEx
                );
            }
        }
    }
}