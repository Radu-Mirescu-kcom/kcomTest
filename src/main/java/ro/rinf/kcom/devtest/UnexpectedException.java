package ro.rinf.kcom.devtest;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super(message);
    }
    public UnexpectedException(String message,Throwable t) {
        super(message,t);
    }
}
