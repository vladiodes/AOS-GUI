package utils.Json.PolymorphDeserializer;

public @interface JsonSubtype {
    Class<?> clazz();

    String name();
}