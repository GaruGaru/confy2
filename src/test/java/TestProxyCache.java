import it.modularity.confy.proxy.ProxyCache;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestProxyCache {

    @Test
    public void testProxyCacheCreate() {
        assertThat(ProxyCache.create(method -> null)).isNotNull();
    }

    @Test
    public void testCacheGet() {
        ProxyCache proxyCache = ProxyCache.create(Method::getName);
        Method method = String.class.getMethods()[0];
        Object value = proxyCache.get(method);
        assertThat(value).isEqualTo(method.getName());
    }

    @Test
    public void testCachePersist() {
        ProxyCache proxyCache = ProxyCache.create(method -> UUID.randomUUID().toString());
        Method method = String.class.getMethods()[0];
        Object firstsValue = proxyCache.get(method);
        for (int i = 0; i < 3; i++)
            assertThat(proxyCache.get(method)).isEqualTo(firstsValue);
    }


}
