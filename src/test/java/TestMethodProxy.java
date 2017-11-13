import interfaces.TestableInterfaceMethods;
import interfaces.TestableInterfaceWithDefaults;
import interfaces.TestableInterfaceWithoutKeys;
import it.modularity.confy.Confy;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestMethodProxy {

    @Test
    public void testInterfaceWithDefaults() {
        TestableInterfaceWithDefaults impl = Confy.implement(TestableInterfaceWithDefaults.class);
        assertThat(impl.getFloat()).isEqualTo(1.5F);
        assertThat(impl.getString()).isEqualTo("default-string");
        assertThat(impl.getInt()).isEqualTo(0);
        assertThat(impl.getBoolean()).isTrue();
    }

    @Test
    public void testInterfaceWithoutKeys() {
        TestableInterfaceWithoutKeys impl = Confy.fromProperty("test-interface-01").to(TestableInterfaceWithoutKeys.class);
        assertThat(impl.getFloat()).isEqualTo(1.5F);
        assertThat(impl.getString()).isEqualTo("test");
        assertThat(impl.getInt()).isEqualTo(1);
        assertThat(impl.getBoolean()).isFalse();
    }

    @Test
    public void testInterfaceWithPropertiesImplementation() {
        TestableInterfaceWithDefaults impl = Confy.fromProperty("test-interface-01").to(TestableInterfaceWithDefaults.class);
        assertThat(impl.getInt()).isEqualTo(1);
        assertThat(impl.getString()).isEqualTo("test");
        assertThat(impl.getBoolean()).isFalse();
    }


    @Test
    public void testInterfaceWithEnvImpl() {
        System.setProperty("float", "1.1");
        System.setProperty("int", "2");
        System.setProperty("string", "test-env");
        System.setProperty("boolean", "false");
        TestableInterfaceWithDefaults impl = Confy.fromProperty("test-interface-01").withEnv().to(TestableInterfaceWithDefaults.class);
        assertThat(impl.getInt()).isEqualTo(2);
        assertThat(impl.getString()).isEqualTo("test-env");
        assertThat(impl.getFloat()).isEqualTo(1.1F);
        assertThat(impl.getBoolean()).isFalse();
    }

    @Test(expected = Exception.class)
    public void testFloatParseException() {
        System.setProperty("float", "1.1A");
        TestableInterfaceWithDefaults impl = Confy.fromProperty("test-interface-01").withEnv().to(TestableInterfaceWithDefaults.class);
        float value = impl.getFloat();
    }

    @Test
    public void testBooleanParseException() {
        // https://docs.oracle.com/javase/7/docs/api/java/lang/Boolean.html#parseBoolean(java.lang.String)
        System.setProperty("boolean", "b3");
        TestableInterfaceWithDefaults impl = Confy.fromProperty("test-interface-01").withEnv().to(TestableInterfaceWithDefaults.class);
        boolean value = impl.getBoolean();
    }

    @Test
    public void testInterfaceWithMethodsOnly() {
        String value = "10.10.10.10";
        System.setProperty("confy.host", value);
        TestableInterfaceMethods impl = Confy.fromProperty("test-interface-01").withEnv().to(TestableInterfaceMethods.class);
        assertThat(impl.confyHost()).isEqualTo(value);
        assertThat(impl.getConfyHost()).isEqualTo(value);
        assertThat(impl.standardConfyHost()).isEqualTo(value);
        assertThat(impl.envConfyHost()).isEqualTo(value);
    }

}
