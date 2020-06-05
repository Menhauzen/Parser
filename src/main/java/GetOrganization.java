import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetOrganization {
    List<Organizaciya> magazines = new ArrayList<Organizaciya>();
    public Document getPage(String url) throws IOException {
        return Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
                .referrer("https://www.google.kz/")
                .get();

    }

    public String[] getNames(Document page){
        Element getElement = page.select("div[class=searchResults__list]").first();
        Elements headers = getElement.select("div[class=miniCard__content]");
        Elements content = headers.select("div[class=miniCard__content]");
        Elements names = content.select("h3[class=miniCard__headerTitle]");
        return names.toString().replaceAll("\\<.*?\\> ?", "").split("\\r?\\n");
    }

    public  String[] getAddress(Document page){
        String [] s;
        Element getElement = page.select("div[class=searchResults__list]").first();
        Elements headers = getElement.select("div[class=miniCard__content]");
        Elements content = headers.select("div[class=miniCard__content]");
        Elements addressAndFilials = content.select("div[class=miniCard__desc _address]");
        Elements addressOnly = addressAndFilials.select("span[class=miniCard__address]");
        s = addressOnly.toString().replaceAll("\\<.*?\\> ?", "").split("\\r?\\n");
        for(int i = 0; i < s.length;i++){
            s[i] = s[i].replaceAll("&nbsp;"," ");
        }
        return s;
    }

    public String getUrl(Document page){
        String s[];
        String str;
        Elements getElement = page.select("nav[class=pagination__pages]");
        Elements getSpan = getElement.select("a[class=pagination__page]");
        s = getSpan.toString().replaceAll("\\<a href=?","").split("\\r?\\n");
        for(int i = 0; i<s.length; i++){
            s[i] = s[i].replaceAll("class=.*?\\>*?.\\<.a","");
            s[i] = s[i].replaceAll("\\>","");
            s[i] = s[i].replace(("\""), "");
        }
        str = s[0].substring(0,s[0].length()-2);
        return str;
    }

    public void dataToDb(String[] names, String[] address){
         final Connection con = DbConnection.createConnection();
         String SQL_Create_Magazin = "INSERT INTO poest(name,address)VALUES(?,?)";


           for(int i = 0; i<address.length; i++){
               magazines.add(new Organizaciya(names[i],address[i]));
               try (PreparedStatement pstmt = con.prepareStatement(SQL_Create_Magazin, Statement.RETURN_GENERATED_KEYS)){

pstmt.setString(1,names[i]);
pstmt.setString(2,address[i]);
pstmt.executeUpdate();

               }catch (SQLException ex){
                   ex.printStackTrace();
               }catch (IndexOutOfBoundsException ie){
                   i++;
                   continue;

               }
               System.out.println("Добавление " + i + " в бд завершено");
               System.out.println(names[i]);
           }

    }

    public Integer getPageCount(Document page){
        String result;
        Elements header = page.select("div[class=searchResults__header]");
        Elements pageCount = header.select("h2[class=searchResults__headerName]");
        result = pageCount.toString().replaceAll("\\<.*?\\> ?","");
        result = result.replaceAll("[^ 0-9]","");
        int res = Integer.valueOf(result.replaceAll(" ",""));
        System.out.println(res);
       return res;
    }

}
