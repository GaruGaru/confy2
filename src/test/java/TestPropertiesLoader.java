import it.modularity.confy.factory.PropertiesLoader;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestPropertiesLoader {

    @Test
    public void testLoadProperty(){
        assertThat(PropertiesLoader.load("test-single").isPresent()).isTrue();
    }


    @Test
    public void testLoadInvalidProperty(){
        assertThat(PropertiesLoader.load("_---_-ark1").isPresent()).isFalse();
    }


    @Test
    public void testLoadPropertyLocal(){
        assertThat(PropertiesLoader.fromLocal("test-single.properties").isPresent()).isFalse();
    }

    @Test
    public void testLoadClassPath(){
        assertThat(PropertiesLoader.fromClassPath("test-single.properties").isPresent()).isTrue();
    }

}
