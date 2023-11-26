package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        List<TimeSeries> lst = new ArrayList<>();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String word : words) {
            lst.add(map.weightHistory(word, startYear, endYear));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(words, lst);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;

    }
}
