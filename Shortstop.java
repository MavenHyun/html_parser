/**
 * Created by Maven Hyun on 2016-12-28.
 */
public class Shortstop
{
    public String name;
    public String play;
    public Double lev;
    public Double wpa;
    public Double REa;

    public Shortstop(String p)
    {
        name = "교체";
        play = p;
        lev = 0.0;
        wpa = 0.0;
        REa = 0.0;
    }

    public Shortstop(String n, String p, Double l, Double w, Double r)
    {
        name = n;
        play = p;
        lev = l;
        wpa = w;
        REa = r;
    }

    public void PrintOut()
    {
        System.out.println(name + "\t" + play + "\t" + lev + "\t" + wpa + "\t" + REa);
    }

}
