package ro.rinf.kcomTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

public class VendingMachine {
    private Inventory inventory;
    private String propertiesPath;

    public VendingMachine() {}

    public VendingMachine(Properties properties,String path) {
        inventory = new Inventory(properties);
        this.propertiesPath = path;
    }

	public Collection<Coin> getOptimalChangeFor(int pence) {
		return new NoCoinLimitOptimalChangeContext(pence).getChangeFor();
	}

    public Collection<Coin> getChangeFor(int pence) {
        try {
            Collection<Coin> toReturn = new WithCoinLimitOptimalChangeContext(inventory,pence).getChangeFor();
            save(inventory);
            return toReturn;
        } catch(Exception ex) {
            throw new RuntimeException("Unexpected exception: " + ex.getMessage());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public synchronized void save(Inventory inventory) throws URISyntaxException, FileNotFoundException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(propertiesPath);
        File file = new File(url.toURI().getPath());
        try( PrintStream printStream = new PrintStream(new FileOutputStream(file))){
            printStream.print(inventory.toString());
        }
    }
}