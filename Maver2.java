import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

/**
 * Created by Maven Hyun on 2017-02-01.
 */
/*public class MyFrame extends JFrame
{
    MyFrame(String title)
    {
        super(title);
        setSize(600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        makeUI();
        setVisible(true);
    }
    private void makeUI() {
        MyPanel p = new MyPanel();
        add(p, BorderLayout.CENTER);
    }
}*/




public class Maver2
{
    public static void main(String[] args)throws Exception {
        //MyFrame frame = new MyFrame("Main frame");
        Document naver = null;
        Document naver_sub = null;
        Document naver_sub2 = null;
        int num = 0;
        //2009~2011 롯데, 2012~2016 개두산 홍성흔
        //2009~2012 SK, 2013~2016 NC 이호준
        //2009~2016 LG 정성훈

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C:\\Users\\Arthur Kim\\Desktop\\pjs\\bin\\phantomjs.exe");


        for (int start = 2016; start < 2017; start++)
        {
            Batter batter = new Batter("김민성");
            batter.year = start;
            for (int j = 4; j < 12; j++)
            {
                try {
                    naver = Jsoup.connect("http://sports.news.naver.com/kbaseball/schedule/index.nhn?month=" + j + "&year=" + start + "&teamCode=WO").get();
                    for (int a = 0; a < 20; a++)
                    {
                        try {
                            Element table_gr = naver.select("#nhn #content .sch_baseball .tb_wrap .sch_tb table tbody tr td .td_btn").get(a);
                            Element ahref = table_gr.select("a").get(0);
                            String link = ahref.attr("href");
                            String game_id = link.substring(link.lastIndexOf("=") + 1);

                            WebDriver ghost = new PhantomJSDriver(caps);
                            try {
                                ghost.get("http://sports.news.naver.com/gameCenter/gameRecord.nhn?gameId=" + game_id);
                                naver_sub = Jsoup.parse(ghost.getPageSource());
                            } catch (Exception e3) {
                            }
                            Parse_gameresult(naver_sub, batter, game_id);

                            table_gr = naver.select("#nhn #content .sch_baseball .tb_wrap .sch_tb2 table tbody tr td .td_btn").get(a);
                            ahref = table_gr.select("a").get(0);
                            link = ahref.attr("href");
                            game_id = link.substring(link.lastIndexOf("=") + 1);
                            try {
                                ghost.get("http://sports.news.naver.com/gameCenter/gameRecord.nhn?gameId=" + game_id);
                                naver_sub2 = Jsoup.parse(ghost.getPageSource());
                            } catch (Exception e3) {
                            }
                            Parse_gameresult(naver_sub2, batter, game_id);
                            ghost.close();
                            ghost.quit();
                        }
                        catch (Exception d) {
                        }
                    }
                }
                catch (Exception e) {
                }
                System.out.println(j + "월 끝");
            }
            System.out.println(start + "년 끝");
            batter.PrintOut();
        }

    }

    public static void Parse_gameresult(Document naver_sub, Batter batter, String game_id)
    {
        for (int ha = 0; ha < 2; ha++)
        {
            Element table = naver_sub.select("#wrap #container #content .ad_result_skin .article .t_result_board2 tbody").get(ha);
            Elements rows = table.select("tr");
            for (int k = 0; k < rows.size(); k++)
            {
                Element row = rows.get(k);
                Elements column = row.select("td");
                Element name = row.select("th a").get(0);
                String z_name = name.text();
                if (z_name.contains(batter.name))
                {
                    for (int l = 1; l < 13; l++)
                    {
                        String play = column.get(l).text();
                        if ((!play.isEmpty())
                                /*&&(!play.contains("삼진"))
                                &&(!play.contains("4구"))
                                &&(!play.contains("사구"))
                                &&(!play.contains("스낫"))
                                &&(!play.contains("고4"))
                                &&(!play.contains("직"))
                                &&(!play.contains("홈"))
                                &&(!play.contains("중"))
                                &&(!play.contains("좌"))
                                &&(!play.contains("우"))
                                &&(!play.contains("포"))
                                &&(!play.contains("파"))
                                &&(!play.contains("비"))*/
                                &&(play.contains("병")))
                        {
                            batter.link.add(game_id);
                            batter.plays.add(play);
                        }
                    }
                }
            }
        }
    }
}
