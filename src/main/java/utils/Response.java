package utils;

public class Response<T> {
    private T value;
    private String msg;
    private boolean errorOccurred;

    public static <T> Response<T> OK(T value){
        return new Response<>(value);
    }

    public static <T> Response<T> OK(T value, String msg){
        return new Response<>(value,msg);
    }

    public static <T> Response<T> FAIL(Exception exception){
        return new Response<>(exception);
    }

    private Response(T value){
        this.value = value;
        errorOccurred = false;
    }

    private Response(T value, String msg){
        this.value = value;
        this.msg = msg;
        errorOccurred = false;
    }

    private Response(Exception exception){
        this.msg = exception.getMessage();
        errorOccurred = true;
    }

    public T getValue(){
        return value;
    }


    public boolean hasErrorOccurred() {
        return errorOccurred;
    }

    public String getMessage() {
        return msg;
    }
}
