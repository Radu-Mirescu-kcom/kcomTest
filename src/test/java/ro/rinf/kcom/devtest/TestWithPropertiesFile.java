package ro.rinf.kcom.devtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class TestWithPropertiesFile {
    VendingMachine vendingMachine;

    @Before
    public void setup() throws Exception {
        initializeThePropertiesFile();
        vendingMachine = new VendorMachineBuilder().build();
    }

    private void initializeThePropertiesFile() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("coin-inventory.properties");
        try( PrintStream printStream = new PrintStream(new File(url.toURI().getPath()),"UTF-8") ){
            printStream.print("100=11\n50=24\n20=0\n10=99\n5=200\n2=11\n1=23\n");
        } catch( URISyntaxException | FileNotFoundException | UnsupportedEncodingException ex ) {
            throw new UnexpectedException("Test initialize properties file error: " + ex.getMessage());
        }
    }

    public Properties propertiesFileContent() throws Exception {
        Properties properties = new Properties();
        try (final InputStream stream =
                 this.getClass().getClassLoader().getResourceAsStream("coin-inventory.properties")) {
            properties.load(stream);
            return properties;
        }
    }

    @Test
    public void verifyUsingThePropertiesFile() throws Exception {
        Collection<Coin> result = vendingMachine.getChangeFor(1);
        assertEquals(result.size(),1);
        Assert.assertEquals(result.iterator().next(), Coin.ONE);
        Properties props = propertiesFileContent();
        assertEquals("22",props.getProperty("1"));
    }
}
