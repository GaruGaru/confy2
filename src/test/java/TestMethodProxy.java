import interfaces.TestableInterfaceWithDefaults;
import it.modularity.confy.Confy;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestMethodProxy {

    @Test
    public void testInterfaceWithDefaults(){
        TestableInterfaceWithDefaults impl = Confy.implement(TestableInterfaceWithDefaults.class);
        assertThat(impl.getFloat()).isEqualTo(1.5F);
        assertThat(impl.getString()).isEqualTo("default-string");
        assertThat(impl.getInt()).isEqualTo(0);
    }


    @Test
    public void testInterfaceWithPropertiesImplementation() {
        TestableInterfaceWithDefaults impl = Confy.implement(TestableInterfaceWithDefaults.class, "test-interface-01", false);
        assertThat(impl.getInt()).isEqualTo(1);
        assertThat(impl.getString()).isEqualTo("test");
    }

    @Test
    public void testInterfaceWithEnvImpl() {
        System.setProperty("float", "1.1");
        System.setProperty("int", "2");
        System.setProperty("string", "test-env");
        TestableInterfaceWithDefaults impl = Confy.implement(TestableInterfaceWithDefaults.class, "test-interface-01", true);
        assertThat(impl.getInt()).isEqualTo(2);
        assertThat(impl.getString()).isEqualTo("test-env");
        assertThat(impl.getFloat()).isEqualTo(1.1F);
    }

}
