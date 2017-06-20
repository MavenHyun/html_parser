import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maven Hyun on 2016-12-27.
 */
public class Maven
{
    public void getData(int i, int age1, int age2, BufferedWriter out) throws IOException {
        ArrayList<Toosoo> p = new ArrayList<Toosoo>();

        Double war = 0.0; Double fip = 0.0; Double whip = 0.0;
        Double k9 = 0.0; Double bb9 = 0.0; Double hr9 = 0.0; Double hit = 0.0; Double bipa = 0.0;

        Document doc = null;
        try
        {
            doc = Jsoup.connect("http://www.statiz.co.kr/stat.php?mid=stat&re=1&se=0&te=&tm=&ty=0&qu=75&po=0&as="
                    + i +"&ae=" + i  + "&hi=&un=&pl=&da=1&o1=WAR&o2=OutCount&de=1&lr=0&tr=&cv=&ml=1&sn=100&pa=0&si=&cn=&ys=" + age1 + "&ye=" + age2).get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Element table = doc.select(".main .contents .contents_main_full .table_stz").get(0);
        Elements rows = table.select("tr");

        Document doc2 = null;
        try 
        {
            doc2 = Jsoup.connect("http://www.statiz.co.kr/stat.php?opt=0&sopt=0&re=1&ys=" + age1 + "&ye=" + age2 +
                    "&se=0&te=&tm=&ty=0&qu=75&po=0&as=" + i + "&ae=" + i + "&hi=&un=&pl=&da=2&o1=FIP&de=0&o2=WAR&lr=0&tr=&cv=&ml=1&sn=100&si=&cn=").get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Element table2 = doc2.select(".main .contents .contents_main_full .table_stz").get(0);
        Elements rows2 = table2.select("tr");

        for (int j = 0; j < rows.size(); j++)
        {
            Element row = rows.get(j);
            try
            {
                    /*32 = war, 29=fip, 28=whip, */
                war = Double.parseDouble(row.select("td").get(32).text());
                fip = Double.parseDouble(row.select("td").get(29).text());
                whip = Double.parseDouble(row.select("td").get(28).text());
            }
            catch (IndexOutOfBoundsException e)
            {
            }
            Element row2 = rows2.get(j);
            try
            {
                    /*10=k9 11=bb9 12=hr9 15=bipa 17=hit*/
                k9 = Double.parseDouble(row2.select("td").get(9).text());
                bb9 = Double.parseDouble(row2.select("td").get(10).text());
                hr9 = Double.parseDouble(row2.select("td").get(11).text());
                bipa = Double.parseDouble(row2.select("td").get(14).text());
                hit = Double.parseDouble(row2.select("td").get(16).text());
            }
            catch (IndexOutOfBoundsException e)
            {
            }
            catch (NumberFormatException e2)
            {
            }
            Toosoo temp = new Toosoo(war, fip, whip, k9, bb9, hr9, bipa, hit);
            p.add(temp);
        }

        for (Toosoo t : p)
        {
            if (t.bb9 != 0)
            {
                out.write(t.war + "\t" + t.bb9 + "\t" + t.k9 + "\t" + t.hr9 + "\t" + t.fip + "\t" + t.whip + "\t" + t.bipa + "\t" + t.hit);
                out.newLine();
            }
        }
    }



    public void getSS(ArrayList<Game> list)
    {
        for (Game g : list)
        {
            try
            {
                try
                {
                    Document doc = Jsoup.connect("http://www.statiz.co.kr/boxscore.php?opt=5&date=" + g.date).get();
                    Element table = doc.select(".main .contents .table_stz").get(4);
                    Elements rows = table.select("tr");
                    if (!g.home)
                    {
                        String name = "오류";
                        Element lineup = doc.select(".main .contents .table_stz").get(2);
                        Elements rows3 = lineup.select("tr");
                        for (int i = 1; i < rows3.size(); i++)
                        {
                            Element line = rows3.get(i);
                            Element pos = line.select("td").get(2);
                             if (pos.text().contains("유격수"))
                            {
                                name = (line.select("td").get(1)).text();
                            }
                        }

                        Elements rows2 = rows.select(".evenrow_stz");
                        for (int i = 0; i < rows2.size(); i++)
                        {
                            Element row = rows2.get(i);
                            Element play = row.select("td").get(4);
                            if (play.text().contains("유격수"))
                            {
                                Element lev = row.select("td").get(7);
                                Element REa = row.select("td").get(9);
                                Element WPa = row.select("td").get(11);
                                try
                                {
                                    Shortstop temp = new Shortstop(name, play.text(), Double.parseDouble(lev.text()), Double.parseDouble(WPa.text()), Double.parseDouble(REa.text()));
                                    temp.PrintOut();
                                }
                                catch (NumberFormatException n)
                                {
                                    Shortstop temp = new Shortstop(play.text());
                                    name = "교체";
                                    temp.PrintOut();
                                }
                            }
                        }
                    }
                    else
                    {
                        String name = "오류";
                        Element lineup = doc.select(".main .contents .table_stz").get(3);
                        Elements rows3 = lineup.select("tr");
                        for (int i = 1; i < rows3.size(); i++)
                        {
                            Element line = rows3.get(i);
                            Element pos = line.select("td").get(2);
                            if (pos.text().contains("유격수"))
                            {
                                name = (line.select("td").get(1)).text();
                            }
                        }

                        Elements rows2 = rows.select(".oddrow_stz");
                        for (int i = 0; i < rows2.size(); i++)
                        {
                            Element row = rows2.get(i);
                            Element play = row.select("td").get(4);
                            if (play.text().contains("유격수"))
                            {
                                Element lev = row.select("td").get(7);
                                Element REa = row.select("td").get(9);
                                Element WPa = row.select("td").get(11);
                                try
                                {
                                    Shortstop temp = new Shortstop(name, play.text(), Double.parseDouble(lev.text()), Double.parseDouble(WPa.text()), Double.parseDouble(REa.text()));
                                    temp.PrintOut();
                                }
                                catch (NumberFormatException n)
                                {
                                    Shortstop temp = new Shortstop(play.text());
                                    name = "교체";
                                    temp.PrintOut();
                                }
                            }
                        }
                    }
                }
                catch (IOException e1)
                {
                }
            }
            catch (IndexOutOfBoundsException e)
            {
            }
            System.out.println("성공");
        }
    }
}
