/**
 * Created by Maven Hyun on 2017-04-20.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class OhJaeWon
{
    public static void main(String[] args) throws Exception
    {
        Offense doosan = new Offense();

        doosan.fetch_results("개두산_경기일자.txt");

        return;




    }
}
