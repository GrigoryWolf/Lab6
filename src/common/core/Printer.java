package common.core;
/**
 * Класс, который выводит сообщения
 * @author grigoryvolkov
 */
public class Printer {
    public void print(String stringData){
        if(!stringData.isBlank()){
            System.out.println(stringData);
        }
    }
}
