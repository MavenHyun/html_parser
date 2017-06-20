/**
 * Created by Maven Hyun on 2016-11-19.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InterruptedIOException;
import java.util.ArrayList;


public class HTML_Parser {
    public static void main(String[] args)throws Exception
    {
        for (int i = 2019; i < 2018; i++)
        {
            String n = Integer.toString(i) + ".txt";
            ArrayList<Pitcher> pitchers = new ArrayList<Pitcher>();
            FileReader f = new FileReader(n);
            BufferedReader b = new BufferedReader(f);
            String line = null;
            while ( (line = b.readLine()) != null)
            {
                String[] text = line.split("\t");
                String fa = text[0];
                String name = text[1];
                String yr = text[2];
                String money = text[3];

                Pitcher p = new Pitcher(name, Integer.parseInt(fa), Double.parseDouble(money), Integer.parseInt(yr));
                pitchers.add(p);
            }

            for (Pitcher p : pitchers)
            {
                System.out.println("이름: " + p.name + " 퐈 연도: " + p.fa + " " + p.num + "년 " + p.money + "억");

                try
                {
                    Document doc = Jsoup.connect("http://www.statiz.co.kr/player.php?name=" + p.name).get();
                    Element table = doc.select(".main .contents .table_stz").get(1);
                    Elements rows = table.select("tr");

                    for (int j = 2; j < rows.size(); j++)
                    {
                        Element row = rows.get(j);
                        Element col = row.select("td").get(0);
                        try
                        {
                            p.year.add(Integer.parseInt(col.text()));
                        }
                        catch (java.lang.NumberFormatException q)
                        {
                        }
                        col = row.select("td").get(3);
                        try
                        {
                            p.war.add(Double.parseDouble(col.text()));
                        }
                        catch (java.lang.NumberFormatException q)
                        {
                        }
                        if (p.year.get(p.year.size()-1) >= p.fa)
                        {
                            p.year.remove(p.year.size()-1);
                            p.war.remove(p.war.size()-1);
                        }
                    }
                }

                catch (IndexOutOfBoundsException e)
                {
                    Document doc = Jsoup.connect("http://www.statiz.co.kr/player.php?name=" + p.name).get();
                    Element table = doc.select(".main .contents .table_stz").get(0);
                    Elements rows = table.select("tr");

                    for (int j = 2; j < rows.size(); j++)
                    {
                        Element row = rows.get(j);
                        Element col = row.select("td").get(0);
                        try
                        {
                            p.year.add(Integer.parseInt(col.text()));


                        }
                        catch (java.lang.NumberFormatException q)
                        {
                        }
                        col = row.select("td").get(3);
                        try
                        {
                            p.war.add(Double.parseDouble(col.text()));
                        }
                        catch (java.lang.NumberFormatException q)
                        {
                        }
                        if (p.year.get(p.year.size()-1) >= p.fa)
                        {
                            p.year.remove(p.year.size()-1);
                            p.war.remove(p.war.size()-1);
                        }
                    }
                }
                p.war.remove(p.war.size()-1);
                p.LR();
                p.value();
            }

            System.out.println("end");
        }
    /*
        ArrayList<String> yr = new ArrayList<String>();
        ArrayList<String> war = new ArrayList<String>();
        for (int i = 0; i < pitcher.size(); i++)
        {
            try
            {
                Document doc = Jsoup.connect("http://www.statiz.co.kr/player.php?name=" + pitcher.get(i)).get();
                Element table = doc.select(".main .contents .table_stz").get(1);
                Elements rows = table.select("tr");
                war.add(pitcher.get(i));
                yr.add("연도");
                for (int j = 2; j < rows.size(); j++)
                {
                    Element row = rows.get(j);
                    Element col = row.select("td").get(0);
                    yr.add(col.text());
                    col = row.select("td").get(3);
                    war.add(col.text());
                }
            }
            catch (IndexOutOfBoundsException e)
            {
                Document doc = Jsoup.connect("http://www.statiz.co.kr/player.php?name=" + pitcher.get(i)).get();
                Element table = doc.select(".main .contents .table_stz").get(0);
                Elements rows = table.select("tr");
                war.add(pitcher.get(i));
                for (int j = 2; j < rows.size(); j++)
                {
                    Element row = rows.get(j);
                    Element col = row.select("td").get(0);
                    yr.add(col.text());
                    col = row.select("td").get(3);
                    war.add(col.text());
                }
            }
            System.out.print(i + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < war.size(); i++)
        {
            System.out.println(yr.get(i) + "\t" + war.get(i));
        }
    */
    }

}
