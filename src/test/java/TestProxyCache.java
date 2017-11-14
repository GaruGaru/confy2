import it.modularity.confy.proxy.ProxyCache;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestProxyCache {

    @Test
    public void testProxyCacheCreate() {
        assertThat(ProxyCache.create(method -> null)).isNotNull();
    }

    @Test
    public void testCache() {
        ProxyCache proxyCache = ProxyCache.create(Method::getName);
        Method method = String.class.getMethods()[0];
        Object value = proxyCache.get(method);
        assertThat(value).isEqualTo(method.getName());
    }

}
