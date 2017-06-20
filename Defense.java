import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Maven Hyun on 2016-12-28.
 */
public class Defense
{
    public static void main(String[] args)throws Exception
    {
        Maven m = new Maven();
        ArrayList<Game> list = new ArrayList<Game>();
        String fn = "김하성.txt";
        FileReader fr = new FileReader(fn);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ( (line = br.readLine()) != null)
        {
            String[] text = line.split("\t");
            String day = text[0];
            Game temp = new Game(day);
            temp.home_away();
            try
            {
                if (Integer.parseInt(text[1]) == 2)
                {
                    temp.lg();
                }
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
            }
            list.add(temp);
        }
        m.getSS(list);





        int a = 0;




    }
}
