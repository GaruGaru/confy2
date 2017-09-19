package it.modularity.confy.proxy;

import it.modularity.confy.Confy;
import it.modularity.confy.annotations.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ConfyProxy implements InvocationHandler {

    private static final Class<Annotation>[] ANNOTATIONS = new Class[]{
            Param.Boolean.class,
            Param.String.class,
            Param.Integer.class,
            Param.Float.class,
    };

    private final Confy conf;

    public ConfyProxy(Confy conf) {
        this.conf = conf;
    }

    private static boolean isParam(Method method) {
        for (Class<Annotation> aClass : ANNOTATIONS)
            if (method.isAnnotationPresent(aClass))
                return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <T> T proxying(Class<T> iClass, Confy conf) {
        return (T) Proxy.newProxyInstance(iClass.getClassLoader(), new Class<?>[]{iClass}, new ConfyProxy(conf));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!isParam(method))
            return method.invoke(proxy, args);
        return getParamValue(method);
    }

    private Object getParamValue(Method method) {
        for (Class<Annotation> annotation : ANNOTATIONS) {
            if (method.isAnnotationPresent(annotation)) {
                Annotation concrete = method.getAnnotation(annotation);
                try {
                    Class<? extends Annotation> aClass = concrete.getClass();
                    String key = (String) aClass.getDeclaredMethod("key").invoke(concrete);
                    key = key.isEmpty() ? keyFromMethod(method) : key;
                    Object defaultValue = aClass.getDeclaredMethod("defaultValue").invoke(concrete);
                    defaultValue = Adapter.cast(defaultValue, method.getReturnType());
                    return Adapter.adapt(conf.get(key, defaultValue), method.getReturnType());
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Unable to find 'defaultValue' method on " + method.getClass());
                }
            }
        }
        return null;
    }

    private String keyFromMethod(Method method) {
        String key = method.getName().toLowerCase();
        if(key.startsWith("get"))
            key = key.substring(3, key.length());
        return key;
    }


}
