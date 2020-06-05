import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static java.lang.Math.ceil;

public class Download {
    public static void main(String[] args) {
        String url ="https://2gis.kz" ;
        String changeableUrl;
        String urlEnd = "?queryState=center%2F71.478767%2C51.141879%2Fzoom%2F11";
        int pageCount;
        GetOrganization getOrganization = new GetOrganization();

        try {
            Document page = getOrganization.getPage("https://2gis.kz/astana/search/%D0%9F%D0%BE%D0%B5%D1%81%D1%82%D1%8C?queryState=center%2F71.479111%2C51.14231%2Fzoom%2F11");
           pageCount = (int) Math.ceil(AllStreets.getPageCount(page) / 12);
            getOrganization.dataToDb(getOrganization.getNames(page),getOrganization.getAddress(page));

            changeableUrl=getOrganization.getUrl(page);
            System.out.println(changeableUrl);
            for(int i = 2 ; i<=pageCount; i++){
            //     Thread.sleep(3000);
                page=getOrganization.getPage(url + changeableUrl  + i + urlEnd);
                getOrganization.dataToDb(getOrganization.getNames(page),getOrganization.getAddress(page));
                System.out.println(i);
            }

            System.out.println(pageCount);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
