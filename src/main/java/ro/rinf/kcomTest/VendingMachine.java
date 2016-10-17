package ro.rinf.kcomTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

public class VendingMachine {
    private Inventory inventory;
    private String propertiesPath;
    private static final int MAX_TRIALS = 10;
    private static final double INCREASE_DURATION_FACTOR = 2d;

    public VendingMachine() {}

    public VendingMachine(Properties properties,String path) {
        inventory = new Inventory(properties);
        this.propertiesPath = path;
    }

	public Collection<Coin> getOptimalChangeFor(int pence) {
		return new NoCoinLimitOptimalChangeContext(pence).getChangeFor();
	}

    public Collection<Coin> getChangeFor(int pence) {
        long waitInterval = 1;
        long nbTrials = 1;
        try {
            while(true) {
                Inventory inventoryClone = inventory.clone();
                ChangeContext changeContext = new WithCoinLimitOptimalChangeContext(inventoryClone,pence);
                Collection<Coin> toReturn = changeContext.getChangeFor();
                if( save(toReturn) ) {
                    return toReturn;
                } else {
                    nbTrials++;
                    if( nbTrials > MAX_TRIALS ) {
                        throw new RuntimeException(String.format("UNABLE TO RESOLVE getChangeFor(%d). TIME OUT!",pence));
                    }
                    try {
                        Thread.currentThread().sleep(waitInterval);
                    } catch(InterruptedException itEx) {
                    }
                    waitInterval *= INCREASE_DURATION_FACTOR;
                }
            }
        } catch( URISyntaxException | FileNotFoundException ex ) {
            throw new RuntimeException("Saving error: " + ex.getMessage());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public synchronized boolean save(Collection<Coin> coins) throws URISyntaxException, FileNotFoundException {
        Inventory newInventory = inventory.clone();
        InventoryIterator inventoryIterator = new InventoryIterator(newInventory);
        Optional<Coin> inventoryCoin = inventoryIterator.next();
        for( Coin coin:coins) {
            while(true) {
                if( inventoryCoin.isPresent() ) {
                    if( inventoryCoin.get() == coin ) {
                        if(!inventoryIterator.takeOne()){
                            return false;
                        } else {
                            break;
                        }
                    } else {
                        inventoryIterator.next();
                    }
                } else {
                    throw new RuntimeException("Unexpected empty inventory!");
                }
            }
        }

        inventory = newInventory;
        URL url = Thread.currentThread().getContextClassLoader().getResource(propertiesPath);
        File file = new File(url.toURI().getPath());
        try( PrintStream printStream = new PrintStream(new FileOutputStream(file))){
            printStream.print(inventory.toString());
        }
        return true;
    }
}