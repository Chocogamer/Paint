// imageButton does not work yet

// Imports
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

// GUI Class
public class GUI {
    // Instance Variables
    private JFrame frame;
    private SidebarPanel sidebarPanel;
    private DrawingPanel drawingPanel;

    // Default Constructor
    public GUI(int width, int height) {
        // frame Initialization
        frame = new JFrame("Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(width, height);

        // sidebarPanel Initialization
        sidebarPanel = new SidebarPanel();

        // drawingPanel Initialization
        drawingPanel = new DrawingPanel();

        // frame Configurations
        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(drawingPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // SidebarPanel Class (private)
    private class SidebarPanel extends JPanel {
        // Instance Variables
        private JButton colorButton;
        private JButton pencilButton;
        private JButton lineButton;
        private JButton rectangleButton;
        private JButton ellipseButton;
        private JButton imageButton;
        private JButton eraserButton;

        // Default Constructor
        public SidebarPanel() {
            colorButton = new JButton("Color...");
            colorButton.addActionListener(new MyActionListener());

            pencilButton = new JButton("Pencil");

            lineButton = new JButton("Line");

            rectangleButton = new JButton("Rectangle");

            ellipseButton = new JButton("Ellipse");

            imageButton = new JButton("Image");

            eraserButton = new JButton("Eraser");

            this.setLayout(new GridLayout(7, 1));
            this.add(colorButton);
            this.add(pencilButton);
            this.add(lineButton);
            this.add(rectangleButton);
            this.add(ellipseButton);
            this.add(imageButton);
            this.add(eraserButton);

            MyActionListener mAL = new MyActionListener();
            colorButton.addActionListener(mAL);
            pencilButton.addActionListener(mAL);
            lineButton.addActionListener(mAL);
            rectangleButton.addActionListener(mAL);
            ellipseButton.addActionListener(mAL);
            imageButton.addActionListener(mAL);
            eraserButton.addActionListener(mAL);
        }

        // MyActionListener Class (private)
        private class MyActionListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                if(event.getSource().equals(colorButton)) {
                    JColorChooser colorChooser = new JColorChooser();
                    Color c = JColorChooser.showDialog(colorChooser, "Choose", Color.CYAN);
                    drawingPanel.setCurrentColor(c);
                }
                if(event.getSource().equals(pencilButton)) {
                    drawingPanel.setCurrentMode("pencil");
                }
                if(event.getSource().equals(lineButton)) {
                    drawingPanel.setCurrentMode("line");
                }
                if(event.getSource().equals(rectangleButton)) {
                    drawingPanel.setCurrentMode("rectangle");
                }
                if(event.getSource().equals(ellipseButton)) {
                    drawingPanel.setCurrentMode("ellipse");
                }
                if(event.getSource().equals(imageButton)) {
                    drawingPanel.setCurrentMode("image");
                }
                if(event.getSource().equals(eraserButton)) {
                    drawingPanel.setCurrentMode("eraser");
                }
            }
        }
    }

    // DrawingPanel Class (private)
    private class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
        // Instance Variables
        private Color currentColor;
        private String currentMode;
        private int x1, y1, cx, cy;
        private ArrayList<DrawItem> drawItemList;

        // Default Constructor
        public DrawingPanel() {
            currentMode = "pencil";
            currentColor = Color.BLACK;
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.setFocusable(true);

            drawItemList = new ArrayList<DrawItem>();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            for(DrawItem d : drawItemList) {
                g.setColor(d.color);
                if(d.drawMode.equals("pencil")) {
                    g2.fillOval(d.cx, d.cy, 10, 10);
                }
                if(d.drawMode.equals("line")) {
                    g2.setStroke(new BasicStroke(2));
                    g2.drawLine(d.cx, d.cy, d.x1, d.y1);
                }
                if(d.drawMode.equals("rectangle")) {
                    g2.drawRect(d.cx, d.cy, 50, 50);
                }
                if(d.drawMode.equals("ellipse")) {
                    g2.drawOval(d.cx, d.cy, 50, 50);
                }
            }
            
            g2.setColor(currentColor);

            if(currentMode.equals("pencil")) {
                g2.fillOval(cx, cy, 10, 10);
            }
            if(currentMode.equals("line")) {
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(cx, cy, x1, y1);
            }
            if(currentMode.equals("rectangle")) {
                g2.drawRect(cx, cy, 50, 50);
            }
            if(currentMode.equals("ellipse")) {
                g2.drawOval(cx, cy, 50, 50);
            }
            if(currentMode.equals("eraser")) {
                drawItemList.clear();
                repaint();
            }
        }

        public void setCurrentColor(Color color) {
            currentColor = color;
        }

        public void setCurrentMode(String mode) {
            currentMode = mode;
        }

        public void mouseDragged(MouseEvent event) {
            x1 = event.getX();
            y1 = event.getY();
            cx = x1;
            cy = y1;
            drawItemList.add(new DrawItem(x1, y1, cx, cy, this.currentMode, this.currentColor));
            repaint();
        }

        public void mousePressed(MouseEvent event) {
            cx = event.getX();
            cy = event.getY();
        }

        public void mouseExited(MouseEvent event) {}
    
        public void mouseClicked(MouseEvent event) {
            cx = event.getX();
            cy = event.getY();
            drawItemList.add(new DrawItem(x1, y1, cx, cy, this.currentMode, this.currentColor));
            repaint();
        }
    
        public void mouseEntered(MouseEvent event) {}
    
        public void mouseReleased(MouseEvent event) {}
    
        public void mouseMoved(MouseEvent event) {}
        
        // DrawItem Class (private)
        private class DrawItem {
            public int x1, y1, cx, cy;
            public String drawMode;
            public Color color;
            public DrawItem(int x1, int y1, int cx, int cy, String drawMode, Color color) {
                this.x1 = x1;
                this.y1 = y1;
                this.cx = cx;
                this.cy = cy;
                this.drawMode = drawMode;
                this.color = color;
            }
        }
    }

}