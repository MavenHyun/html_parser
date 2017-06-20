/**
 * Created by Maven Hyun on 2016-12-28.
 */
public class Game
{
    String date;
    boolean home;

    public void home_away()
    {
        if (date.contains("고척돔"))
        {
            home = true;
        }
    }

    public void lg()
    {
        home = false;
    }
    public Game(String d)
    {
        date = d;
        boolean home = false;
    }
}
