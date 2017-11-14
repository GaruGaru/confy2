package it.modularity.confy.proxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ProxyCache {

    public static ProxyCache create(Function<Method, Object> methodSolver) {
        return new ProxyCache(methodSolver);
    }

    private Map<Method, Object> cacheMap;

    private final Function<Method, Object> methodSolver;

    public ProxyCache(Function<Method, Object> methodSolver) {
        this.methodSolver = methodSolver;
        this.cacheMap = new HashMap<>();
    }

    public Object get(Method method) {
        if (this.cacheMap.containsKey(method)) {
            return this.cacheMap.get(method);
        } else {
            Object value = methodSolver.apply(method);
            this.cacheMap.put(method, value);
            return value;
        }
    }


}
