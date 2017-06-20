import javafx.collections.transformation.SortedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maven Hyun on 2016-12-22.
 */

public class AgingCurve
{
    public static void main(String[] args)throws Exception
    {
        for (int i = 18; i < 41; i++)
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(i+".txt"));
            out.write("WAR\tBB/9\tK/9\thr/9\tfip\twhip\tbipa\t피안타율");
            out.newLine();
            ArrayList<Toosoo> p = new ArrayList<Toosoo>();
            Maven m = new Maven();
            m.getData(i, 1982, 1999, out);
            m.getData(i, 2000, 2008, out);
            m.getData(i, 2009, 2016, out);
            out.close();


            System.out.println("성공 " + i);
        }

    }
}
