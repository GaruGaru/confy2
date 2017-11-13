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
        Confy confy = Confy.create("test-single");
        assertThat(confy.get("single")).isEqualTo("1");
    }

    @Test
    public void testCreationFromMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "true");
        Confy confy = Confy.create(map);
        assertThat(confy).isNotNull();
        assertThat(confy.toMap()).isEqualTo(map);
    }

    @Test
    public void testCreationFromEnv() {
        System.setProperty("test", "env");
        Confy conf = Confy.create("test-simple-01", true);
        assertThat(conf.get("test")).isEqualTo("env");
    }

    @Test
    public void testCreationFromEnvWithNormalize() {
        System.setProperty("NORMALIZED_KEY", "env");
        Confy conf = Confy.create("test-simple-01", true);
        assertThat(conf.get("normalized.key")).isEqualTo("env");
    }

    @Test
    public void testCreationFromProperty() {
        String[] keys = new String[]{"testing", "number", "name"};
        System.setProperty("testing", "env");
        Confy conf = Confy.create("test-simple-01", false);
        assertThat(conf.toMap()).containsOnlyKeys(keys);
        assertThat(conf.get("testing")).isNotEqualTo("env");
    }


    @Test
    public void testCreationFromEnvAndProperty() {
        String[] keys = new String[]{"testing", "number", "name", "conf-env"};
        System.setProperty("testing", "env");
        System.setProperty("conf-env", "env");
        Confy conf = Confy.create("test-simple-01", true);
        assertThat(conf.toMap()).containsKeys(keys);
        assertThat(conf.get("testing")).isEqualTo("env");
    }

    @Test
    public void testCreate() {
        assertThat(Confy.create()).isNotNull();
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
    public void testKeyNormalizerNoOp(){
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("test")).isEqualTo("test");
    }

    @Test
    public void testKeyNormalizerLower(){
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("TEST")).isEqualTo("test");
    }

    @Test
    public void testKeyNormalizerUppercase(){
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("TEST_FORMAT")).isEqualTo("test.format");
    }

    @Test
    public void testKeyNormalizerCamelCase(){
        Confy confy = Confy.create();
        assertThat(confy.normalizeKey("TestMethod")).isEqualTo("test.method");
    }

    @Test
    public void testToString() {
        assertThat(Confy.create().toString()).isNotEmpty();
    }

}
