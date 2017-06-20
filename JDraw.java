import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.FileReader;


public class JDraw
{
    public static void main(String[] arg)
    {
        MyFrame f = new MyFrame("Main frame");
    }
}

class MyFrame extends JFrame
{
    MyFrame(String title)
    {
        super(title);
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        makeUI();
        setVisible(true);
    }
    private void makeUI()
    {
        MyPanel p = new MyPanel();
        add(p, BorderLayout.CENTER);
    }
}

class MyPanel extends JPanel
{
    protected void paintComponent(Graphics g)
    {
        try {
            String fn = "김민성타구분포도.txt";
            Batter batter = new Batter("김민성");
            FileReader f = new FileReader(fn);
            BufferedReader b = new BufferedReader(f);
            String line = null;

            while ((line = b.readLine()) != null)
            {
                String[] text = line.split("\t");
                batter.x.add(text[0]);
                batter.y.add(text[1]);
                batter.r.add(text[2]);
            }

            Graphics2D g2=(Graphics2D)g;

            float dash0[] = {1,0f};
            float dash3[] = {3,3f};
            g2.translate(0, 0);                // 원점을 (300, 300)로 이동시킨다.

            g2.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1,dash3,0));

            for (int i=-600; i<=600; i = i+20)
            {
                g2.draw(new Line2D.Float(-600, i, 600, i));     // x축과 평행선을 그린다.
            }
            for (int j=-600; j<=600; j = j+20)
            {
                g2.draw(new Line2D.Float(j, -600, j, 600));     // y축과 평행선을 그린다.
            }

            g2.setStroke(new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1,dash0,0));
            g2.draw(new Line2D.Float(-600, 0, 600, 0));     // x축을 그린다.
            g2.draw(new Line2D.Float(0, -600, 0, 600));     // y축을 그린다.
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    /* Construct a shape and draw it */



            for (int i = 0; i < batter.x.size(); i++ )
            {
                if (batter.r.get(i).contains("아웃"))
                {
                    g2.setColor(Color.BLUE);
                }
                else if (batter.r.get(i).contains("안타"))
                {
                    g2.setColor(Color.GREEN);
                }
                else
                {
                    g2.setColor(Color.RED);
                }
                double dx = Double.parseDouble(batter.x.get(i));
                double dy = Double.parseDouble(batter.y.get(i));
                Ellipse2D.Double shape = new Ellipse2D.Double(dx, dy, 0.1, 0.1);
                g2.draw(shape);
            }


        }
        catch (Exception e) {
            Batter batter = new Batter("임시");
        }
    }
}
