import interfaces.TestableInterfaceWithDefaults;
import it.modularity.confy.Confy;
import it.modularity.confy.factory.ConfigFactory;
import it.modularity.confy.proxy.ConfyProxy;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestConfyProxy {

    @Test
    public void testProxying() {
        Confy confy = Confy.create();
        TestableInterfaceWithDefaults proxed = ConfyProxy.proxying(TestableInterfaceWithDefaults.class, confy);
        assertThat(proxed).isNotNull();
    }

    @Test
    public void testRealMethodInvoke() throws Throwable {
        Confy confy = Confy.create();
        confy.put("confy", "wow");
        new ConfyProxy(confy).invoke(
                confy,
                Confy.class.getDeclaredMethod("clear"),
                null
        );
    }

    @Test
    public void testAnnotatedMethodInvoke() throws Throwable {
        Confy confy = Confy.create();
        confy.put("string", "wow");

        Object a = new ConfyProxy(confy).invoke(
                null,
                TestableInterfaceWithDefaults.class.getDeclaredMethod("getString"),
                null
        );

        assertThat(a).isInstanceOf(String.class);

        assertThat(String.valueOf(a)).isEqualTo("wow");

    }

    @Test(expected = Exception.class)
    public void testInvalidMethodInvoke() throws Throwable {
        Confy confy = Confy.create();
        confy.put("confy", "wow");
        new ConfyProxy(confy).invoke(
                confy,
                Confy.class.getDeclaredMethod("nope"),
                null
        );
    }

}
