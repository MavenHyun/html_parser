/**
 * Created by Maven Hyun on 2017-01-27.
 */
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




public class Maver
{
    public static void main(String[] args)throws Exception
    {
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
                                ghost.get("http://sports.news.naver.com/gameCenter/gameResult.nhn?gameId=" + game_id);
                                naver_sub = Jsoup.parse(ghost.getPageSource());
                            } catch (Exception e3) {
                            }
                            Parse_gameresult(naver_sub, batter, game_id);

                            table_gr = naver.select("#nhn #content .sch_baseball .tb_wrap .sch_tb2 table tbody tr td .td_btn").get(a);
                            ahref = table_gr.select("a").get(0);
                            link = ahref.attr("href");
                            game_id = link.substring(link.lastIndexOf("=") + 1);
                            try {
                                ghost.get("http://sports.news.naver.com/gameCenter/gameResult.nhn?gameId=" + game_id);
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
            batter.PrintXY();
        }
    }



    public static void Parse_gameresult(Document naver_sub, Batter batter, String game_id)
    {
        Elements results = naver_sub.select("#wrap #container #content .ad_result_skin .article .inner .section_chart .data_wrap .ground_area .ico");

        for (int k = 0; k < results.size(); k++)
        {
            Element result = results.get(k);
            Element target = result.select(".tooltip, .bg_top, strong").get(0);
            Element type = result.select("p em").get(0);
            if (target.text().contains(batter.name))
            {
                String coord = result.attr("style");
                String portion1[] = coord.split("px");
                String portion2[] = portion1[0].split(" ");
                String portion3[] = portion1[1].split(" ");
                batter.x.add(portion2[1]);
                batter.y.add(portion3[2]);
                batter.r.add(type.text());
                System.out.println(target.text() + " 추가 완료!");
            }
        }
    }
}
