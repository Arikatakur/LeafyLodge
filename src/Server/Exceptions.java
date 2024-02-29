package Server;

public class Exceptions extends Exception{
    public Exceptions(String str){
        super(str);
    }
    public Exceptions(String str, Throwable cause){
        super(str, cause);
    }
}