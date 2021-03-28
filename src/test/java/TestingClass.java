import com.harleyoconnor.javautilities.pair.Pair;
import com.harleyoconnor.javautilities.util.IntegerUtils;
import org.junit.Test;

/**
 * @author Harley O'Connor
 */
public final class TestingClass {

    @Test
    public void test () {
        System.out.println("\n\nStart of Java Utilities Testing\n");

        System.out.println(IntegerUtils.isPrime(155));
        System.out.println(IntegerUtils.round(Math.PI, 7));
        System.out.println(Math.round(3.524));

        Pair<Integer, Integer> firstPair = Pair.immutable(4, 6);
        Pair<Integer, Integer> secondPair = Pair.partiallyMutable(4, 6);

        Pair<String, Integer> pair = Pair.mutable();

        final Pair<String, Integer> pairClone = pair.duplicate();

        System.out.println(pair == pairClone);
        System.out.println(pair.equals(pairClone));

        System.out.println(firstPair.equals(secondPair));
        System.out.println(pair.hashCode());
        System.out.println(firstPair);

        System.out.println("\nEnd of Java Utilities Testing\n\n");
    }

}
