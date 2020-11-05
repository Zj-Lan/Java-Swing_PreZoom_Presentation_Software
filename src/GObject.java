import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/

class GAttributes
{
    protected double x, y;
    protected double x2,y2;
    protected int width, height;
    protected Color col;
    protected Boolean filled = Boolean.FALSE;
    protected Boolean visible = Boolean.TRUE;
    protected int lineWidth = 3;
    protected BasicStroke stroke;

    public GAttributes(double x, double y, Color col, Boolean filled, int lineWidth)
    {
        this.x = x;
        this.y = y;
        this.col = col;
        this.filled = filled;
        this.lineWidth = lineWidth;
    }
}

class GObject
{
    protected HashMap<Integer,GAttributes> state_Attributes_map = new HashMap<>(); //Attributes of each state
    //int current_State = 0;
    GAttributes cur_Attributes;

    protected GObject(double x, double y, Color col, Boolean filled, int lineWidth)
    {
        //this.id = id;
//        this.x = x;
//        this.y = y;
//        this.col = col;
//        this.filled = filled;
//        this.lineWidth = lineWidth;
        GAttributes attributes = new GAttributes(x,y,col,filled,lineWidth);
        state_Attributes_map.put(getCurrent_State(), attributes);
        updateCur_Attributes();
    }

    public int getCurrent_State()
    {
        return StateManager.current_State;
    }

    private GAttributes getCur_Attributes()
    {
        return state_Attributes_map.get(getCurrent_State());
    }

    private void updateCur_Attributes()
    {
        this.cur_Attributes = getCur_Attributes();
    }

    public Color getCol()
    {
        return cur_Attributes.col;
    }

    public void setCol(Color col)
    {
        cur_Attributes.col = col;
    }

    public double getX()
    {
        return cur_Attributes.x;
    }

    public void setX(double x)
    {
        cur_Attributes.x = x;
    }

    public double getY()
    {
        return cur_Attributes.y;
    }

    public void setY(double y)
    {
        cur_Attributes.y = y;
    }

    public Boolean getFilled()
    {
        return cur_Attributes.filled;
    }

    public void setFilled(Boolean filled)
    {
        cur_Attributes.filled = filled;
    }

    public Boolean getVisible()
    {
        return cur_Attributes.visible;
    }

    public void setVisible(Boolean visible)
    {
        cur_Attributes.visible = visible;
    }

    public int getLineWidth()
    {
        return cur_Attributes.lineWidth;
    }

    public void setLineWidth(int lineWidth)
    {
        cur_Attributes.lineWidth = lineWidth;
    }

    protected void draw(Graphics2D g)
    {
    }

    protected boolean inShape(int x, int y)
    {
        return false;
    }
}

class Rectangle extends GObject
{
    public Rectangle(double x, double y, Color col, Boolean filled, int lineWidth, int w, int h)
    {
        super(x, y, col, filled, lineWidth);
        cur_Attributes.width = w;
        cur_Attributes.height = h;
    }

    public int getH()
    {
        return cur_Attributes.height;
    }

    public void setH(int h)
    {
        cur_Attributes.height = h;
    }

    public int getW()
    {
        return cur_Attributes.width;
    }

    public void setW(int w)
    {
        cur_Attributes.width = w;
    }

    public boolean inShape(int mx, int my)
    {
        double x = getX(), y = getY();
        int w = getW(), h = getH();
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void draw(Graphics2D g)
    {
        g.setColor(cur_Attributes.col);
        double x = getX(), y = getY();
        int w = getW(), h = getH();

        if (getFilled()) g.fillRect((int)x, (int)y, w, h);
        else
        {
            g.setStroke(new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(new Rectangle2D.Double(x, y, w, h));
        }
    }

}

class Oval extends GObject
{
    public Oval(int x, int y, Color col, Boolean filled, int lineWidth, int w, int h)
    {
        super(x, y, col, filled, lineWidth);
        cur_Attributes.width = w;
        cur_Attributes.height = h;
    }

    public int getH()
    {
        return cur_Attributes.height;
    }

    public void setH(int h)
    {
        cur_Attributes.height = h;
    }

    public int getW()
    {
        return cur_Attributes.width;
    }

    public void setW(int w)
    {
        cur_Attributes.width = w;
    }

    public boolean inShape(int mx, int my)
    {
        double x = getX(), y = getY();
        int w = getW(), h = getH();
        //return Math.sqrt((mx - x) * (mx - x) + (my - y) * (my - y)) < r;
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void draw(Graphics2D g)
    {
        g.setColor(cur_Attributes.col);
        double x = getX(), y = getY();
        int w = getW(), h = getH();

        if (getFilled()) g.fillOval((int)x, (int)y, w, h);
        else
        {
            g.setStroke(new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(new Ellipse2D.Double(x, y, w, h));
        }
    }

}