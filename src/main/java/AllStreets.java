import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllStreets {
    public static Integer getPageCount(Document page){
        String result;
        Elements header = page.select("div[class=searchBar__mediaTabs]");
        Elements pageCount = header.select("h2[class=.searchBar__mediaTab._active]");
        Elements pageActiveCount = pageCount.select("span[class=searchBar__mediaTabTextDescription,searchBar__mediaTabTextValue]");
        System.out.println(page);
        result = pageCount.toString().replaceAll("\\<.*?\\> ?","");
        result = result.replaceAll("[^ 0-9]","");
        int res = Integer.valueOf(result.replaceAll(" ",""));
        System.out.println(res);
        return res;
    }
    public static String[] getAddress(Document page){
        String b;
        Elements getElement = page.select("div[class=mediaResults__resultsItem]");
        Elements address = getElement.select("span[class=mediaMiniCard__address]");
        b = address.toString().replaceAll("<span class=\"mediaMiniCard__address\" title=\"","");
       b = b.replaceAll("\\<.*?\\> *?","");
        b = b.replaceAll("\"\\>.*[[а-яА-Я_0-9]]","");
        System.out.println(b.replaceAll("&nbsp;",""));
        return b.split("\\r?\\n");
    }

    public static void main(String[] args) throws IOException {
        int pageCount;
        GetOrganization getOrganization = new GetOrganization();
        Document page = getOrganization.getPage("https://2gis.kz/astana/search/%D0%9F%D0%BE%D0%B5%D1%81%D1%82%D1%8C?queryState=center%2F71.479111%2C51.14231%2Fzoom%2F11");
        getAddress(page);
    }
}
