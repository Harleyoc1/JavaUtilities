import com.harleyoconnor.javautilities.convention.NamingConvention;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Harley O'Connor
 */
public final class TestingClass {

    @Test
    public void test () {
        System.out.println("\n\nStart of Java Utilities Testing\n");

        this.testNamingConventions();

        System.out.println("\nEnd of Java Utilities Testing\n\n");
    }

    private void testNamingConventions() {
        final var conventions = List.of(NamingConvention.CAMEL_CASE, NamingConvention.PASCAL_CASE,
                NamingConvention.SNAKE_CASE, NamingConvention.SCREAMING_SNAKE_CASE, NamingConvention.CAMEL_SNAKE_CASE,
                NamingConvention.PASCAL_SNAKE_CASE, NamingConvention.KEBAB_CASE, NamingConvention.SCREAMING_KEBAB_CASE,
                NamingConvention.TRAIN_CASE);

        var lastConvention = conventions.get(0);
        var lastStr = "thisIsMyString";

        System.out.println("Initial String: " + lastStr + " Follows: " + lastConvention.doesFollow(lastStr) + " [" +
                NamingConvention.get(lastConvention.name()).get().name() + "]");

        for (int i = 1; i < conventions.size(); i++) {
            lastStr = lastConvention.convertTo(conventions.get(i), lastStr);
            System.out.println("Converted: " + lastStr + " Follows: " + conventions.get(i).doesFollow(lastStr) + " [" +
                    NamingConvention.get(conventions.get(i).name()).get().name() + "]");
            lastConvention = conventions.get(i);
        }
    }

}
