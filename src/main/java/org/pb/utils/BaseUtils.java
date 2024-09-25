package org.pb.utils;

import org.modelmapper.ModelMapper;
import org.pb.utils.function.ThrowingSupplier;

import java.util.function.Function;

public final class BaseUtils {
    public static <S, D> Function<ModelMapper, D> map(S s, Class<D> clazz) {
        return mapper -> {
            if (s == null) {
                return null;
            }

            D result = null;

            try {
                result = clazz.newInstance();
                mapper.map(s, result);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return result;
        };
    }

    public static <T> T doTry(ThrowingSupplier<T> func) {
        try {
            return func.get();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
