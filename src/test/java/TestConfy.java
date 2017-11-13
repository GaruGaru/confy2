import it.modularity.confy.Confy;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TestConfy {

    @Test
    public void testConfyCreation() {
        Confy confy = Confy.empty();
        assertThat(confy).isNotNull();
        assertThat(confy.toMap()).isEmpty();
    }

    @Test
    public void testCreationFromProperties() {
        Confy confy = Confy.fromProperty("test-single");
        assertThat(confy.get("single")).isEqualTo("1");
    }

    @Test
    public void testCreationFromMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "true");
        Confy confy = Confy.fromMap(map);
        assertThat(confy).isNotNull();
        assertThat(confy.toMap()).isEqualTo(map);
    }

    @Test
    public void testCreate() {
        assertThat(Confy.create()).isNotNull();
    }

    @Test
    public void testFluentCreateArgs() {
        Confy confy = Confy.create()
                .withArgs("--test=true");
        assertThat(confy.get("test")).isEqualTo("true");
    }

    @Test
    public void testFluentCreateEnv() {
        System.setProperty("test", "true");
        Confy confy = Confy.create().withEnv();
        assertThat(confy.get("test")).isEqualTo("true");
    }

    @Test
    public void testFluentCreateOverride() {
        System.setProperty("test", "true");
        Confy confy = Confy.create()
                .withEnv()
                .withArgs("--test=false");
        assertThat(confy.get("test")).isEqualTo("false");
    }

    @Test
    public void testFluentCreateProperty() {
        System.setProperty("testing", "env");
        Confy confy = Confy.create()
                .withEnv()
                .withProperty("test-simple-01");
        assertThat(confy.get("testing")).isEqualTo("true");
    }

    @Test(expected = RuntimeException.class)
    public void testFluentCreateArgsFormatException(){
        Confy.fromArgs("a b c");
    }
    @Test
    public void testPut() {
        Confy confy = Confy.create();
        confy.put("test", true);
        assertThat(confy.toMap()).containsKeys("test");
        assertThat(confy.get("test")).isEqualTo(true);
    }

    @Test
    public void testPutIfAbsent() {
        Confy confy = Confy.create();
        confy.put("test", true);
        confy.putIfNotPresent("test", false);
        assertThat(confy.toMap()).containsKeys("test");
        assertThat(confy.get("test")).isEqualTo(true);
    }

    @Test
    public void testClear() {
        Confy confy = Confy.create();
        confy.put("test", true);
        confy.clear();
        assertThat(confy.toMap()).isEmpty();
    }

    @Test
    public void testKeyNormalizerNoOp() {
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("test")).isEqualTo("test");
    }

    @Test
    public void testKeyNormalizerLower() {
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("TEST")).isEqualTo("test");
    }

    @Test
    public void testKeyNormalizerUppercase() {
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("TEST_FORMAT")).isEqualTo("test.format");
    }

    @Test
    public void testKeyNormalizerCamelCase() {
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("TestMethod")).isEqualTo("test.method");
    }

    @Test
    public void testToString() {
        assertThat(Confy.create().toString()).isNotEmpty();
    }

}
