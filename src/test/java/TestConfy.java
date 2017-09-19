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
}
