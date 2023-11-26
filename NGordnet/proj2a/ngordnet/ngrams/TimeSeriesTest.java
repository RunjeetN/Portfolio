package ngordnet.ngrams;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }
    @Test
    public void testDividedBy(){
        TimeSeries t1 = new TimeSeries();
        TimeSeries t2 = new TimeSeries();
        t1.put(1993, 2.0);
        t2.put(1993, 4.0);
        t1.put(1994, 5.0);
        t2.put(1994, 12.0);
        TimeSeries t3 = new TimeSeries();
        t3.put(1994, 2.0);
        // assert(t1.dividedBy(t3)).isEqualTo(new IllegalArgumentException());
        TimeSeries result = new TimeSeries();
        result.put(1993, 0.5);
        result.put(1994, 0.4166666666666667);
        for(int i = 1993; i < 1995; i++){
            assertThat(t1.dividedBy(t2).get(i)).isWithin(1E-10).of(result.get(i));
        }
    }
} 