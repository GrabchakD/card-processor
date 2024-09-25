package org.pb.utils.function;

public interface ThrowingFunction<T, R> {

    R apply(T t) throws Exception;

    static ThrowingFunction partial() {
        return x -> x;
    }
}
