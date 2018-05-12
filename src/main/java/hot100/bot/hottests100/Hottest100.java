package hot100.bot.hottests100;
import static java.lang.String.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.pattern.util.IEscapeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class Hottest100 {
    private String billboardUrl;
    private List<Song> top50Hottest;
    private List<Song> top100Hottest;

    public Hottest100(String billboardUrl) {
        this.billboardUrl = billboardUrl;
        top50Hottest = new ArrayList<>();
        top100Hottest = new ArrayList<>();
        setTop50Hottest(billboardUrl);
    }

    private void setTop50Hottest(String url) {
        try{
            Document doc = Jsoup.connect(url).get();
            Elements link = doc.getElementsByClass("chart-row");
            for (int i = 0; i < 100; i++) {
                Element elem = link.get(i);
                String artist = elem.getElementsByClass("chart-row__artist").html();

                String formatArtist = Parser.unescapeEntities(artist, false);

                Song song = new Song(formatArtist);
                if (i <= 49){
                    top50Hottest.add(song);
                } else {
                    top100Hottest.add(song);
                }
            }

        } catch (IOException e){
            System.out.println("IO EXCEPTION");
        }
    }

    public String printTo50List() {
        StringBuilder stringBuilder = new StringBuilder();
        int counters = 1;
        for (Song song : top50Hottest) {
            stringBuilder.append(format("(%d) %s\n", counters, song.toString()));
            counters++;
        }
        return stringBuilder.toString();
    }
    public String printTo100List() {
        StringBuilder stringBuilder = new StringBuilder();
        int counters = 51;
        for (Song song : top100Hottest) {
            stringBuilder.append(format("(%d) %s\n", counters, song.toString()));
            counters++;
        }
        return stringBuilder.toString();
    }

    public String getBillboardUrl(){
        return billboardUrl;
    }

    public List<Song> getTop100Hottest() {
        return top100Hottest;
    }
    public List<Song> getTop50Hottest() {
        return top100Hottest;
    }
}
