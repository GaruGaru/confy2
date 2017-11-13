package interfaces;

import it.modularity.confy.annotations.Param;

public interface TestableInterfaceMethods {

    @Param.String(defaultValue = "127.0.0.1")
    String getConfyHost();


    @Param.String(defaultValue = "127.0.0.1")
    String confyHost();

    @Param.String(key = "CONFY_HOST", defaultValue = "127.0.0.1")
    String envConfyHost();

    @Param.String(key = "confy.host", defaultValue = "127.0.0.1")
    String standardConfyHost();

}
