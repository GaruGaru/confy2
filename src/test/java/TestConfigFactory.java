import it.modularity.confy.factory.ConfigFactory;
import org.junit.Test;
import it.modularity.confy.factory.ConfigFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestConfigFactory {

    @Test
    public void testEmpty() {
        assertThat(ConfigFactory.empty()).isEmpty();
    }

    @Test
    public void testFromEnv() {
        System.setProperty("confy", "confy");
        Map<String, Object> env = ConfigFactory.fromEnv();
        assertThat(env.get("confy")).isEqualTo("confy");
    }

    @Test
    public void testFromArgs() {
        String[] args = new String[]{"--log=debug", "test=true", "-str=ing"};
        Map<String, Object> argsMap = ConfigFactory.fromArgs(args);
        assertThat(argsMap).hasSize(3);
        assertThat(argsMap.get("log")).isEqualTo("debug");
        assertThat(argsMap.get("test")).isEqualTo("true");
        assertThat(argsMap.get("str")).isEqualTo("ing");
    }

    @Test(expected = RuntimeException.class)
    public void testFromArgsParseException(){
        ConfigFactory.fromArgs("a b c");
    }

    @Test
    public void testFromProperty() {
        String[] keys = new String[]{"testing", "number", "name"};
        Map<String, Object> env = ConfigFactory.fromProperty("test-simple-01");
        assertThat(env).hasSize(3);
        assertThat(env).containsOnlyKeys(keys);
        Arrays.stream(keys)
                .map(env::get)
                .forEach(value -> assertThat(value).isNotNull());
    }

    @Test
    public void testEnvOverrideProperty() {
        final String override = "confy";

        System.setProperty("testing", override);
        System.setProperty("number", override);
        System.setProperty("name", override);

        String[] keys = new String[]{"testing", "number", "name"};

        Map<String, Object> config = ConfigFactory.fromEnvAndProperties("test-simple-01");

        assertThat(config).containsKeys(keys);

        Arrays.stream(keys)
                .map(config::get)
                .forEach(value -> assertThat(value).isEqualTo(override));
    }

    @Test
    public void testMerge() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "1");
        map1.put("2", "1");

        Map<String, Object> map2 = new HashMap<>();
        map1.put("1", "2");
        map1.put("2", "2");

        Map<String, Object> merged = ConfigFactory.merge(map1, map2);

        assertThat(merged).containsOnlyKeys("1", "2");

        assertThat(merged.get("1")).isEqualTo("2");
        assertThat(merged.get("2")).isEqualTo("2");

    }

    @Test
    public void testMapOf() {
        Properties properties = new Properties();
        properties.put("test", "true");
        Map<String, Object> map = ConfigFactory.mapOf(properties);
        assertThat(map).isNotEmpty();
        assertThat(map).containsOnlyKeys("test");
        assertThat(map.get("test")).isEqualTo(properties.getProperty("test"));

    }

}
