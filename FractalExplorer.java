import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Rectangle2D.Double;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FractalExplorer {
    private int screenSize;
    
    private ArrayList<FractalGenerator> fracGen;
    private Double complRange;
   
    private JFrame window;
    private JButton resetButton;
    private JButton saveButton;
    public JImageDisplay display;
    private JComboBox<String> combo;
    private JLabel descr;
    private JPanel northPanel;
    private JPanel southPanel;

    FractalExplorer(int size){
        this.screenSize = size;
        this.fracGen = new ArrayList<FractalGenerator>();
        fracGen.add(new Mandelbrot());
        fracGen.add(new Tricorn());
        fracGen.add(new BurningShip());
        this.complRange = new Double();
    }

    public void createAndShowGUI(){
        this.window = new JFrame("Fractals");
        this.window.setSize(screenSize, screenSize);
        this.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.display = new JImageDisplay(this.screenSize, this.screenSize);
        window.add(this.display,BorderLayout.CENTER);
        display.addMouseListener(new mouseClicksListener());
        
        this.descr = new JLabel("Select a fractal: ");
        this.combo = new JComboBox<>();

        for(int i = 0; i < fracGen.size(); i++)
            combo.addItem(fracGen.get(i).toString());
        int defaultFrac = 0;
        combo.setSelectedIndex(defaultFrac);
        combo.addActionListener(new comboBoxListener());
        this.northPanel = new JPanel();
        northPanel.add(descr);
        northPanel.add(combo);
        window.add(northPanel, BorderLayout.NORTH);

        this.resetButton = new JButton("Reset");
        this.saveButton = new JButton("Save");
        this.southPanel = new JPanel();
        southPanel.add(resetButton);
        southPanel.add(saveButton);
        window.add(southPanel, BorderLayout.SOUTH);
        resetButton.addActionListener(new resetButtonListener());
        saveButton.addActionListener(new saveButtonListener());

        window.pack();
        window.setVisible(true);
        window.setResizable(false);
        fracGen.get(defaultFrac).getInitialRange(complRange);
        this.drawFractal();
    }

    public void drawFractal(){
        for(int x = 0; x < this.screenSize; x++)
            for(int y = 0; y < this.screenSize; y++)
            {
                double xCoord = FractalGenerator.getCoord (this.complRange.x, this.complRange.x + this.complRange.width,
                                                            this.display.getWidth(), x);
                double yCoord = FractalGenerator.getCoord (this.complRange.y, this.complRange.y + this.complRange.height,
                                                            this.display.getHeight(), y);
                int selectedFrac = combo.getSelectedIndex();

                int numOfIterations = this.fracGen.get(selectedFrac).numIterations(xCoord, yCoord);
                int color = Color.HSBtoRGB(0, 0, 0);
                if (numOfIterations != -1)
                {
                    float hue = 0.7f + (float) numOfIterations / 200f;
                    color = Color.HSBtoRGB(hue, 1f, 1f);
                }
                this.display.drawPixel(x, y, color);
            }
    }

    private class resetButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            int i = combo.getSelectedIndex();

            fracGen.get(i).getInitialRange(complRange);
            FractalExplorer.this.drawFractal();
        }
    }
    private class saveButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JFileChooser chooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);

            int saveFile = chooser.showSaveDialog(window);
            if(saveFile != JFileChooser.APPROVE_OPTION)
                return;
            File selectedFile = chooser.getSelectedFile();
            
            try
            {                               
				ImageIO.write(display.getBufImage(),"png",selectedFile);
                JOptionPane.showMessageDialog(FractalExplorer.this.window,
                                                "Fractal successfuly saved", "File save", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException except) 
            {
				except.printStackTrace();
				JOptionPane.showMessageDialog(FractalExplorer.this.window, "Saving error", "File save", JOptionPane.WARNING_MESSAGE);
			}
        }
    }

    private class mouseClicksListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            double xCoord = FractalGenerator.getCoord (complRange.x, complRange.x + complRange.width,
                                                        display.getWidth(), e.getX());
            double yCoord = FractalGenerator.getCoord (complRange.y, complRange.y + complRange.height,
                                                        display.getHeight(), e.getY());

            int selectedFrac = combo.getSelectedIndex();

            fracGen.get(selectedFrac).recenterAndZoomRange(complRange, xCoord, yCoord, 0.5);
            FractalExplorer.this.drawFractal();                                    
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
    }

    public class comboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            int i = combo.getSelectedIndex();
           
            fracGen.get(i).getInitialRange(complRange);
            FractalExplorer.this.drawFractal();
        }
    }

    public static void main(String[] args) {
        FractalExplorer fractals = new FractalExplorer(600);
        fractals.createAndShowGUI();
    }
}
