package interfaces;

import it.modularity.confy.annotations.Param;

public interface TestableInterfaceWithDefaults {

    @Param.String(key = "string", defaultValue = "default-string")
    String getString();

    @Param.Integer(key = "int", defaultValue = 0)
    int getInt();

    @Param.Float(key = "float", defaultValue = 1.5F)
    float getFloat();

}
