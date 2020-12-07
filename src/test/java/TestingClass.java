import com.harleyoconnor.javautilities.IntegerUtils;
import org.junit.Test;

/**
 * @author Harley O'Connor
 */
public final class TestingClass {

    @Test
    public void test () {
        System.out.println("\n\nStart of Java Utilities Testing\n");

        System.out.println(IntegerUtils.isPrime(155));
        System.out.println(IntegerUtils.roundNumber(Math.PI, 7));
        System.out.println(Math.round(3.524));

        System.out.println("\nEnd of Java Utilities Testing\n\n");
    }

}
