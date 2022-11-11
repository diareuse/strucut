package strucut;

import org.junit.jupiter.api.Test;

public class JavaCompatibilityTest {

    @Test
    public void isCompatible() {
        var strucut = new Strucut(root -> root
                .prop("property")
                .prop("nested", nested -> nested
                        .prop("wow")
                )
        );
        strucut.verify("{\"property\":\"\",\"nested\":{\"wow\":\"\"}}");
    }

}
