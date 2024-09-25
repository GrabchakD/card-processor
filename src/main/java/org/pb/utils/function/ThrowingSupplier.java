package org.pb.utils.function;

public interface ThrowingSupplier<T> {

    T get() throws Exception;

}
