/**
 * Created by Maven Hyun on 2017-04-20.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Offense
{
    ArrayList<BatResult> list_of_games = new ArrayList<BatResult>();

    public void fetch_results(String fn)
    {
        Document fetcher;
        Integer home;
        try
        {
            FileReader f = new FileReader(fn);
            BufferedReader b = new BufferedReader(f);
            String line = null;

            while ((line = b.readLine()) != null)
            {
                BatResult temp = new BatResult();

                String[] text = line.split("\t");
                if (text[0].contains("잠실"))
                {
                    if(text[1].contains("2"))
                    {
                        home = 0;
                        temp.home = false;
                    }
                    else
                    {
                        home = 1;
                        temp.home = true;
                    }
                    fetcher = Jsoup.connect(text[0]).get();
                }
                else
                {
                    home = 1;
                    temp.home = false;
                    fetcher = Jsoup.connect(text[0]).get();
                }
                Elements info = fetcher.getElementsByClass("callout");
                Elements info2 = info.select("span");
                String score = info2.get(0).text();
                temp.score = score;

                Elements tables = fetcher.getElementsByClass("table table-striped");
                Element lineup = tables.get(1 - home);
                Elements list = lineup.select("tbody");
                Elements names = list.select("tr");
                Integer index = 1;
                for (Element name : names)
                {
                    try
                    {
                        String n = name.select("td").get(1).text();
                        temp.table[index][0] = n;
                        index++;
                    }
                    catch(Exception e) {}
                }

                lineup = tables.get(2);
                list = lineup.select("tbody");
                Elements results = list.select("tr");
                Boolean flag = false;
                if (home == 0) flag = true;
                else flag = false;
                Integer num = 1;
                Integer cycle = 1;

                for (Element result : results)
                {
                    try
                    {
                        String h = result.select("td").get(0).text();
                        String r = result.select("td").get(4).text();

                        if (h.contains("초")) flag = !flag;
                        if (h.contains("말")) flag = !flag;

                        if (flag && !r.contains("->") && !r.contains(("*")))
                        {
                            if (num % 10 == 0)
                            {
                                cycle++;
                                num = 1;
                            }
                            temp.table[num % 10][cycle] = r;
                            num++;
                        }
                    }
                    catch(Exception e) {}
                }
                list_of_games.add(temp);
            }
        }
        catch (Exception e) {}
    }

    public void simulate()
    {
        for (BatResult b : list_of_games)
        {
            b.switch_hole("오재원", 2, 8);
            String h = "홈경기";

            if (!b.home) h = "원정경기";
            if (!b.flag) System.out.println(b.score + "\t" + h);
            else
            {
                int i = 1;
                int j = 1;
                int[] bases = new int[] {0, 0, 0};
                int outs = 0;
                int runs = 0;


                while(b.table[i][j].contains("결과"))
                {
                    if (bases[2] == 1) /*주자 3루*/
                    {
                        if (bases[1] == 1 && bases[0] == 1) /*주자 만루*/
                        {
                            if (b.table[i][j].contains("희생"))
                            {
                                if (outs < 2)
                                {
                                    bases[2] = bases[1];
                                    runs++;
                                }
                                outs++;
                            }
                            else if (b.table[i][j].contains("뜬공"))
                            {
                                if (outs < 2)
                                {
                                    bases[2] = bases[1];
                                    runs += 0.7;
                                }
                                outs++;
                            }
                            else if (b.table[i][j].contains("땅볼"))
                            {
                                if (outs == 0)
                                {
                                    bases[2] = bases[1];
                                    bases[1] = 0;
                                    bases[0] = 0;
                                    runs += 0.5;
                                    outs += 2;
                                }
                                else if (outs == 1) outs += 2;
                                else outs++;
                            }
                            else if (b.table[i][j].contains("병살"))
                            {
                                if (outs == 0)
                                {
                                    bases[2] = bases[1];
                                    bases[1] = 0;
                                    bases[0] = 0;
                                    runs += 0.3;
                                    outs += 2;
                                }
                                else if (outs == 1) outs += 2;
                                else outs++;
                            }
                            else if (b.table[i][j].contains("안타"))
                            {
                                bases[2] = 0;
                                bases[1] = bases[0];
                                bases[0] = 1;
                                runs += 1.8;
                            }
                            else if (b.table[i][j].contains("2루타"))
                            {
                                bases[2] = 0;
                                bases[1] = 1;
                                bases[0] = 0;
                                runs += 2.7;
                            }
                            else if (b.table[i][j].contains("3루타"))
                            {
                                bases[2] = 1;
                                bases[1] = 0;
                                bases[0] = 0;
                                runs += 3;
                            }
                            else if (b.table[i][j].contains("홈런"))
                            {
                                bases[2] = 0;
                                bases[1] = 0;
                                bases[0] = 0;
                                runs += 4;
                            }
                            else if (b.table[i][j].contains("실책"))
                            {
                                bases[2] = bases[1];
                                bases[1] = 0;
                                bases[0] = 1;
                                runs += 1.9;
                            }
                            else if (b.table[i][j].contains("삼진"))
                            {
                                outs++;
                            }
                            else
                            {
                                runs += 1;
                            }
                        }

                        else if (bases[1] == 1 && bases[0] == 0) /*주자 23루*/
                        {
                            if (b.table[i][j].contains("희생"))
                            {
                                if (outs < 2)
                                {
                                    bases[2] = bases[1];
                                    runs++;
                                }
                                outs++;
                            }
                            else if (b.table[i][j].contains("뜬공"))
                            {
                                if (outs < 2)
                                {
                                    bases[2] = bases[1];
                                    runs += 0.7;
                                }
                                outs++;
                            }
                            else if (b.table[i][j].contains("땅볼"))
                            {
                                if (outs < 2)
                                {
                                    bases[2] = bases[1];
                                    bases[1] = 0;
                                    runs += 0.6;
                                    outs ++;
                                }
                                else outs++;
                            }
                            else if (b.table[i][j].contains("병살"))
                            {
                                if (outs < 2)
                                {
                                    bases[2] = bases[1];
                                    bases[1] = 0;
                                    runs += 0.6;
                                    outs ++;
                                }
                                else outs++;
                            }
                            else if (b.table[i][j].contains("안타"))
                            {
                                bases[2] = 0;
                                bases[1] = 0;
                                bases[0] = 1;
                                runs += 1.7;
                            }
                            else if (b.table[i][j].contains("2루타"))
                            {
                                bases[2] = 0;
                                bases[1] = 1;
                                bases[0] = 0;
                                runs += 2;
                            }
                            else if (b.table[i][j].contains("3루타"))
                            {
                                bases[2] = 1;
                                bases[1] = 0;
                                bases[0] = 0;
                                runs += 2;
                            }
                            else if (b.table[i][j].contains("홈런"))
                            {
                                bases[2] = 0;
                                bases[1] = 0;
                                bases[0] = 0;
                                runs += 3;
                            }
                            else if (b.table[i][j].contains("실책"))
                            {
                                bases[2] = 0;
                                bases[1] = 0;
                                bases[0] = 1;
                                runs += 1.5;
                            }
                            else if (b.table[i][j].contains("삼진"))
                            {
                                outs++;
                            }
                            else
                            {
                                bases[0] = 1;
                            }
                        }

                        else if (bases[1] == 0 && bases[0] == 1) /*주자 13루*/
                        {

                        }

                        else /*주자 3루*/
                        {

                        }

                    }

                    else
                    {
                        if (bases[1] == 1 && bases[0] == 1) /*주자 12루*/
                        {

                        }

                        else if (bases[1] == 1 && bases[0] == 0) /*주자 2루*/
                        {

                        }

                        else if (bases[1] == 0 && bases[0] == 1) /*주자 1루*/
                        {

                        }

                        else /*주자 없음*/
                        {

                        }
                    }




                }
            }





        }

    }

}
