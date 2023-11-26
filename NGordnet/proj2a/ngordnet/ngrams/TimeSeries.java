package ngordnet.ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        Set<Integer> yearSet = keySet();
        List<Integer> yearList = new ArrayList<>();
        for (Integer year : yearSet) {
            yearList.add(year);
        }
        return yearList;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> values = new ArrayList<>();
        for (Integer year : years()) {
            values.add(get(year));
        }
        return values;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     * <p>
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        List<Integer> minSet = new ArrayList<>();
        if (years().size() <= ts.years().size()) {
            minSet = years();
        } else {
            return ts.plus(this);
        }
        for (Integer year : minSet) {
            if (ts.get(year) == null) {
                newTS.put(year, get(year));
            } else {
                newTS.put(year, get(year) + ts.get(year));
            }
        }
        for (Integer year : ts.years()) {
            if (newTS.get(year) == null) {
                newTS.put(year, ts.get(year));
            }
        }
        return newTS;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     * <p>
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        for (Integer year : years()) {
            if (ts.get(year) == null) {
                throw new IllegalArgumentException();
            }
            newTS.put(year, get(year) / ts.get(year));
        }
        return newTS;
    }
}
