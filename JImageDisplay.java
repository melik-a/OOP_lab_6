import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 * JImageDisplay
 */
public class JImageDisplay extends javax.swing.JComponent {
    //image that we use to display
    private  BufferedImage displImage;
    private Graphics g;
    
    //initializing display image with the specified parans
    JImageDisplay(int width, int height){
        //creating display image
        displImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        g = displImage.getGraphics();
        //set size of image
        java.awt.Dimension size = new java.awt.Dimension(width,height);
        setPreferredSize(size);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        g.drawImage(displImage, 0, 0,displImage.getWidth(), displImage.getHeight(), null);
        this.repaint();
    }

    public void clearImage (){
        g.setColor(Color.black);
        g.drawImage(displImage, 0, 0,displImage.getWidth(), displImage.getHeight(), null);
        //g.fillRect(0, 0, displImage.getWidth(), displImage.getHeight());
        this.repaint();
    }
    
    public void drawPixel(int x, int y, int rgbColor){
        g.setColor(new Color(rgbColor));
        //g.drawImage(displImage, x, y, 1, 1,null);
        //g.fillOval(x, y, 1, 1);
        //g.fillRect(x, y, 1, 1);
        g.drawString("o", x, y);
        this.repaint();
    }

    public BufferedImage getBufImage(){
        return this.displImage;
    }
}