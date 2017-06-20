import java.util.ArrayList;

/**
 * Created by Maven Hyun on 2016-12-26.
 */
public class Pitcher
{
    public String name;
    public int fa;
    public Double money;
    public int num;
    public ArrayList<Double> war;
    public ArrayList<Integer> year;

    public Double mpw;

    public Pitcher(String n, int f, Double m, int y)
    {
        name = n;
        fa = f;
        money = m;
        num = y;
        war = new ArrayList<Double>();
        year = new ArrayList<Integer>();
    }

    public void LR()
    {
        Double a = 0.0; Double b = 0.0;
        Double sum_x = 0.0; Double sum_y = 0.0;
        Double sum1 = 0.0; Double sum2 = 0.0;
        Double avg_x = 0.0; Double avg_y = 0.0;
        int x = 0; Double y = 0.0;

        for (int i = 0; i < war.size(); i++)
        {
            sum_x += year.get(i);
        }

        for (int i = 0; i < year.size(); i++)
        {
            sum_y += war.get(i);
        }
        avg_x = sum_x / year.size();
        avg_y = sum_y / war.size();

        for (int i = 0; i < war.size(); i++)
        {
            sum1 += (war.get(i) * year.get(i));
        }

        for (int i = 0; i < war.size(); i++)
        {
            sum2 += (year.get(i) * year.get(i));
        }

        b = (sum1 - (war.size() * avg_x * avg_y)) / (sum2 - (war.size() * avg_x * avg_x));
        a = avg_y - (b * avg_x);

        for (int i = 0; i < num; i++)
        {
            x = year.get(year.size()-1) + 1;
            y = a + (b * x);
            year.add(x);
            war.add(y);
        }
    }

    public void value()
    {
        Double sum = 0.0;
        for (int i = 0; i < war.size(); i++)
        {
            sum += war.get(i);
        }

        mpw = money / sum;
        String str = String.format("%.4f", mpw);
        System.out.println(name + " 선수 퐈 계약시 war당 투자 금액은 " + str + "억");
    }


}
