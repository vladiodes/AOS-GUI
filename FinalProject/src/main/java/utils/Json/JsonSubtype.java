package utils.Json;

public @interface JsonSubtype {
    Class<?> clazz();

    String name();
}