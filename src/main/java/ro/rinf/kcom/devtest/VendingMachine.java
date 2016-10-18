package ro.rinf.kcom.devtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

import static java.lang.Thread.currentThread;

public class VendingMachine {
    private Inventory inventory;
    private String propertiesPath;
    private static final int MAX_TRIALS = 10;
    private static final double INCREASE_DURATION_FACTOR = 2d;

    public VendingMachine() {
        //needed for getOptimalChangeFor
    }

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
        while(true) {
            Inventory inventoryClone = new Inventory(inventory);
            ChangeContext changeContext = new WithCoinLimitOptimalChangeContext(inventoryClone,pence);
            Collection<Coin> toReturn = changeContext.getChangeFor();
            if( save(toReturn) ) {
                return toReturn;
            }
            nbTrials++;
            if( nbTrials > MAX_TRIALS ) {
                throw new UnexpectedException(String.format("UNABLE TO RESOLVE getChangeFor(%d). TIME OUT!",pence));
            }
            try {
                currentThread().sleep(waitInterval);
            } catch(InterruptedException iEx) {}
            waitInterval *= INCREASE_DURATION_FACTOR;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public synchronized boolean save(Collection<Coin> coins) {
        Inventory newInventory = new Inventory(inventory);
        InventoryIterator inventoryIterator = new InventoryIterator(newInventory);
        for( Coin coin:coins) {
            if( !inventoryIterator.takeOne(coin) ) {
                return false;
            }
        }

        inventory = newInventory;

        if(propertiesPath.equals("")) {
            return true;
        }

        URL url = currentThread().getContextClassLoader().getResource(propertiesPath);
        try {
            try( PrintStream printStream = new PrintStream(new File(url.toURI().getPath()),"UTF-8")){
                printStream.print(inventory.toString());
            }
        } catch( FileNotFoundException | URISyntaxException | UnsupportedEncodingException ex ) {
            throw new UnexpectedException("Save error : " + ex);
        }
        return true;
    }
}