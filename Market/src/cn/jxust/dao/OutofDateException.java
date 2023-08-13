package cn.jxust.dao;

@SuppressWarnings("serial")
public class OutofDateException extends Exception{
    public OutofDateException() {

    }
    public OutofDateException(String message){
        super(message);
    }
}
