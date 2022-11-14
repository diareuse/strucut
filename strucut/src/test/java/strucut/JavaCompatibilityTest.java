package strucut;

import org.junit.jupiter.api.Test;

import static strucut.StructScope.structureScopeOf;

public class JavaCompatibilityTest {

    @Test
    public void isCompatible() {
        final var scope = structureScopeOf(nested -> nested
                .prop("wow")
        );
        var strucut = new Strucut(root -> root
                .prop("property")
                .prop("nested", scope)
        );
        strucut.verify("{\"property\":\"\",\"nested\":{\"wow\":\"\"}}");
    }

}
