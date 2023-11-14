package interfaces;

public interface Saveable<T> {

    public String objectToJson(T object);

    public T jsonToObject(String json);

}
