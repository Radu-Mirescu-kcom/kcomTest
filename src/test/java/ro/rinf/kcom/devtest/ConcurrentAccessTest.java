package ro.rinf.kcom.devtest;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class ConcurrentAccessTest {
    VendingMachineNG vendingMachine;
    int THREAD_NB = 10000;

    @Before
    public void setup() {
        initializeThePropertiesFile();
        PropertyRepository repo = new PropertyRepository();
        vendingMachine = new VendingMachineNG(repo.get());
        vendingMachine.setSaveListener(Optional.of(repo));
    }

    private void initializeThePropertiesFile() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("coin-inventory.properties");
        try( PrintStream printStream = new PrintStream(new File(url.toURI().getPath()),"UTF-8") ){
            printStream.print(String.format("100=%d\n50=%d\n20=%d\n10=%d\n5=%d\n2=%d\n1=%d\n",
                THREAD_NB,THREAD_NB,THREAD_NB,THREAD_NB,THREAD_NB,THREAD_NB,THREAD_NB)
            );
        } catch( URISyntaxException | FileNotFoundException | UnsupportedEncodingException ex ) {
            throw new UnexpectedException("Test initialize properties file error: " + ex.getMessage());
        }
    }

    @Test
    public void testConcurrentAccess() {
        for(int i=0;i<THREAD_NB;i++) {
            new Thread( () -> {
               vendingMachine.getChangeFor(188);
            }).start();
        }
    }
}
