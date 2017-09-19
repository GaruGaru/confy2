package interfaces;

import it.modularity.confy.annotations.Param;

public interface TestableInterfaceWithoutKeys {

    @Param.String()
    String getString();

    @Param.Integer()
    int getInt();

    @Param.Float()
    float getFloat();

    @Param.Boolean()
    boolean getBoolean();

}
