package ro.rinf.kcom.devtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class BasicCoinLimitTest {
    private VendingMachine mainVendingMachine;

    @Before
    public void setup() throws Exception {
        Properties mainProperties = new Properties();
        mainProperties.put("100","11");
        mainProperties.put("50","24");
        mainProperties.put("20","0");
        mainProperties.put("10","99");
        mainProperties.put("5","200");
        mainProperties.put("2","11");
        mainProperties.put("1","23");
        initializeThePropertiesFile();
        mainVendingMachine = new VendingMachine(mainProperties,"");
    }

    private void initializeThePropertiesFile() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("coin-inventory.properties");
        try( PrintStream printStream = new PrintStream(new File(url.toURI().getPath()),"UTF-8") ){
            printStream.print("100=11\n50=24\n20=0\n10=99\n5=200\n2=11\n1=23\n");
        } catch( FileNotFoundException | UnsupportedEncodingException | URISyntaxException ex ) {
            throw new UnexpectedException("Error at initializing properties: " + ex.getMessage());
        }
    }

    @Test
    public void test00() {
        assertEquals(mainVendingMachine.getChangeFor(0).size(),0);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test01() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(1);
        assertEquals(result.size(),1);
        Assert.assertEquals(result.iterator().next(), Coin.ONE);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=200\n2=11\n1=22\n");
    }

    @Test
    public void test02() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(2);
        assertEquals(result.size(),1);
        Assert.assertEquals(result.iterator().next(), Coin.TWO);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=200\n2=10\n1=23\n");
    }

    @Test
    public void test05() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(5);
        assertEquals(result.size(),1);
        Assert.assertEquals(result.iterator().next(), Coin.FIVE);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=99\n5=199\n2=11\n1=23\n");
    }

    @Test
    public void test10() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(10);
        assertEquals(result.size(),1);
        Assert.assertEquals(result.iterator().next(), Coin.TEN);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=98\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test20() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(20);
        assertEquals(result.size(),2);
        Iterator<Coin> it = result.iterator();
        Assert.assertEquals(it.next(), Coin.TEN);
        Assert.assertEquals(it.next(), Coin.TEN);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=24\n20=0\n10=97\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test50() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(50);
        assertEquals(result.size(),1);
        Iterator<Coin> it = result.iterator();
        Assert.assertEquals(it.next(), Coin.FIFTY);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=11\n50=23\n20=0\n10=99\n5=200\n2=11\n1=23\n");
    }

    @Test
    public void test100() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(100);
        assertEquals(result.size(),1);
        Iterator<Coin> it = result.iterator();
        Assert.assertEquals(it.next(), Coin.HUNDRED);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=10\n50=24\n20=0\n10=99\n5=200\n2=11\n1=23\n");
    }


    @Test
    public void test198() {
        Collection<Coin> result = mainVendingMachine.getChangeFor(198);
        assertEquals(result.size(),9);
        Iterator<Coin> it = result.iterator();
        Assert.assertEquals(it.next(), Coin.HUNDRED);
        Assert.assertEquals(it.next(), Coin.FIFTY);
        Assert.assertEquals(it.next(), Coin.TEN);
        Assert.assertEquals(it.next(), Coin.TEN);
        Assert.assertEquals(it.next(), Coin.TEN);
        Assert.assertEquals(it.next(), Coin.TEN);
        Assert.assertEquals(it.next(), Coin.FIVE);
        Assert.assertEquals(it.next(), Coin.TWO);
        Assert.assertEquals(it.next(), Coin.ONE);
        assertEquals(mainVendingMachine.getInventory().toString(),"100=10\n50=23\n20=0\n10=95\n5=199\n2=10\n1=22\n");
    }
}

