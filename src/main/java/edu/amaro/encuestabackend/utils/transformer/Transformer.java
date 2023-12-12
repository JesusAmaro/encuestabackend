package edu.amaro.encuestabackend.utils.transformer;

public interface Transformer<K, T> {
    T transformData(K data);
}