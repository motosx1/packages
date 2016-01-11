package exceptions;

/**
 * Created by Bartosz on 11.01.2016.
 */
public class WrongDataFormatException extends Exception {
    public WrongDataFormatException() { super(); }
    public WrongDataFormatException(String message) { super(message); }
    public WrongDataFormatException(String message, Throwable cause) { super(message, cause); }
    public WrongDataFormatException(Throwable cause) { super(cause); }
}
