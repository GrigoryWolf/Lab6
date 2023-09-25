package common.core.Exceptions;
/**
 * Класс исключение вызывается, когда появляется рекурсия
 * @author grigoryvolkov
 */
public class RecursionException extends Exception{
    public RecursionException(String msg){
        super(msg);
    }
}
