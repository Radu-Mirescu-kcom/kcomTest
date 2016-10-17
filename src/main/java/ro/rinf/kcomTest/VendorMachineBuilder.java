package ro.rinf.kcomTest;

import java.io.InputStream;
import java.util.Properties;

public class VendorMachineBuilder {
    private String resourceFile = "coin-inventory.properties";

    public VendorMachineBuilder setResourceFile(String resourceFile) {
        this.resourceFile = resourceFile;
        return this;
    }

    public VendingMachine build() throws Exception {
        final Properties properties = new Properties();
        try (final InputStream stream =
                 this.getClass().getClassLoader().getResourceAsStream(resourceFile)) {
            properties.load(stream);
            return new VendingMachine(properties,resourceFile);
        }
    }
}
