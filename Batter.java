import java.io.*;
import java.util.ArrayList;

/**
 * Created by Maven Hyun on 2017-01-28.
 */
public class Batter
{
    public int year;
    public String name;
    public ArrayList<String> plays = new ArrayList<String>();
    public ArrayList<String> link = new ArrayList<String>();

    public ArrayList<String> x = new ArrayList<String>();
    public ArrayList<String> y = new ArrayList<String>();
    public ArrayList<String> r = new ArrayList<String>();


    public void PrintXY()
    {
        try {
            String fw = year + " " + name + " 분포.txt";
            BufferedWriter buf = new BufferedWriter(new FileWriter(fw));

            for (int i = 0; i < x.size(); i++)
            {
                buf.write(x.get(i));
                buf.write("\t");
                buf.write(y.get(i));
                buf.write("\t");
                buf.write(r.get(i));
                buf.newLine();
            }
            buf.flush();
            buf.close();
        }
        catch(IOException e) {
        }
    }




    public void PrintOut()
    {
        try {
            String fw = year + " " + name + " 유형.txt";
            BufferedWriter buf = new BufferedWriter(new FileWriter(fw));

            for (int i = 0; i < plays.size(); i++)
            {
                buf.write(link.get(i));
                buf.write("\t");
                buf.write(plays.get(i));
                buf.newLine();
            }
            buf.flush();
            buf.close();

        }
        catch(IOException e) {
        }
    }

    public Batter(String n)
    {
        name = n;
    }
    public Batter() { }
}
