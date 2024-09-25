package org.pb.utils.function;

public interface ThrowingEffect<T> {

    void accept() throws Exception;

}
