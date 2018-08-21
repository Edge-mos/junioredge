package ru.job4j.nonblocking.interfaces.implemented;

import ru.job4j.nonblocking.exeptions.OptimisticExeption;
import ru.job4j.nonblocking.interfaces.Cache;
import ru.job4j.nonblocking.models.Base;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class NonBlockingCache<T extends Base> implements Cache<T> {
    private ConcurrentHashMap<Integer, T> cache = new ConcurrentHashMap<>();

    @Override
    public boolean add(T model) {
        return this.cache.putIfAbsent(model.getId(), model) == null;
    }

    @Override
    public boolean update(T upd) {
        Optional<T> obj = Optional.ofNullable(this.cache.computeIfPresent(upd.getId(), (integer, oldValue) -> {
            if (upd.getVersion() > oldValue.getVersion()) {
                return upd;
            } else throw new OptimisticExeption("Same version or older!");
        }));
        final boolean[] result = {false};
        obj.ifPresent(t -> result[0] = true);
       return result[0];
    }

    @Override
    public T delete(T model) {
        return this.cache.remove(model.getId());
    }
}
