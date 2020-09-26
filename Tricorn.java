import java.awt.geom.Rectangle2D.Double;
public class Tricorn extends FractalGenerator{
    
    public static final int MAX_ITERATIONS = 2000;

    Tricorn(){

    }
    @Override
    public void getInitialRange(Double range) {
        // TODO Auto-generated method stub
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }
    
    @Override
    public int numIterations(double x, double y) {
        // TODO Auto-generated method stub
        double Re = 0;
        double Im = 0;
        int i = 0;
        while ((i < MAX_ITERATIONS) && ((Re*Re + Im*Im) < 4)){
            double tempRe = Re;
            Re = Re * Re - Im * Im + x;
            Im = -2 * tempRe * Im + y;
            i++;
        }
        return i == MAX_ITERATIONS ? -1 : i;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Tricorn";
    }
}