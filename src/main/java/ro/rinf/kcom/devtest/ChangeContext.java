package ro.rinf.kcom.devtest;

import java.util.Collection;

@FunctionalInterface
public interface ChangeContext {
    Collection<Coin> getChangeFor();
}
