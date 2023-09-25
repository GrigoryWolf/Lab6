package common.core.Exceptions;
/**
 * Класс исключение вызывается когда аргументы не валидны
 * @author grigoryvolkov
 */
public class NotValidArgumentsException extends Exception{
    public NotValidArgumentsException(String msg){
        super(msg);
    }
    public NotValidArgumentsException(){}
}
