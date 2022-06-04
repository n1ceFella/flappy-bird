package screens;

import engine.FlappyBirdEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class GameCanvas extends JPanel implements GameConstants
{

    //set bird positions from Interface
    private int birdPositionX = BIRD_POSITION_X;
    private int birdPositionY = BIRD_POSITION_Y_START;

    //game images
    public Image backGroundImage;
    public Image birdImage;
    public Image foreGroundImage;
    public Image pipeUpImage;
    public Image pipeDownImage;

    //local List refer to engine class list of pipes
        private List<FlappyBirdEngine.Drawable> drawables;

    //score counter variable
    private int scoreCounter = 0;

    //check if game is enable
    private boolean inGame = true;

    //Variable for changing images in array
    private int index;

    //image and subimage
    BufferedImage birdBufferedImage;
    BufferedImage birdSubImage;
    int imgNum;

    //constructor loads images, and get references from engine class and list of objects for drawing
    GameCanvas()
    {
        loadImages();
        setIndex();
        setFocusable(true);
        FlappyBirdEngine gameEngine = new FlappyBirdEngine(this);
        this.drawables = gameEngine.getDrawables();

        Timer animator = new Timer(200, e-> {
            imgNum++;
            if (imgNum >= 3) imgNum = 0;
        });
        animator.start();

    }

    //set index for initial bird image
    private void setIndex() { index = 5; }

    //getters and setters for all actions:
    public int getPipeX() {return PIPE_X_START_POSITION;}
    public int getPipeY() {return PIPE_Y_START_POSITION;}
    public int getBirdPositionX(){return birdPositionX;}
    public int getBirdPositionY()
    {
        return birdPositionY;
    }
    public void setBirdPositionY(int y) {birdPositionY = y;}
    public void setScoreCounter(int scoreCounter){this.scoreCounter = scoreCounter;}
    public int getScoreCounter() {return scoreCounter;}
    public void setInGame(boolean inGame) { this.inGame = inGame; }
    public boolean getInGame(){return inGame;}
    public int getGAP(){return GAP;}
    public BufferedImage getBirdSubImage(){return birdSubImage;}

    //get images from another package
    private void loadImages()
    {
        try
        {

            birdBufferedImage = ImageIO.read(getClass().getResource("/resources/final1.png"));
            birdSubImage = birdBufferedImage.getSubimage(0,0,43,31);

            BufferedImage img;

            img = ImageIO.read(getClass().getResource("/resources/flappy_bird_bg.png"));
            backGroundImage = img;
            img = ImageIO.read(getClass().getResource("/resources/flappy_bird_fg.png"));
            foreGroundImage = img;
            img = ImageIO.read(getClass().getResource("/resources/flappy_bird_pipeUp.png"));
            pipeUpImage = img;
            img = ImageIO.read(getClass().getResource("/resources/flappy_bird_pipeBottom.png"));
            pipeDownImage = img;
        }catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void fly(){
        birdSubImage = birdBufferedImage.getSubimage( imgNum * 43,0,43,31);
    }

    //draw all stuff
    public void paintComponent(Graphics g) {

        if (inGame) {

            super.paintComponent(g);

            //Draw background
            g.drawImage(backGroundImage, 0, 0, this);

            //Draw bird
            g.drawImage(birdSubImage, birdPositionX, birdPositionY, this);

            for (FlappyBirdEngine.Drawable drawable : drawables) {
                //Draw upper pipe
                g.drawImage(pipeUpImage, drawable.getX(), drawable.getY(), this);

                //Draw lower pipe
                g.drawImage(pipeDownImage, drawable.getX(), drawable.getY() +
                        pipeUpImage.getHeight(this) + GAP, this);
            }

            //Draw foreground
            g.drawImage(foreGroundImage, 0, backGroundImage.getHeight(this) -
                    foreGroundImage.getHeight(this), this);

            //Draw score
            g.setFont(new Font("Score", Font.BOLD ,16));
            g.drawString("Score: " + scoreCounter, BIRD_POSITION_X / 2, backGroundImage.getHeight(this) -
                    foreGroundImage.getHeight(this) / 2 - 10);

            requestFocus();

        }
    }

    //add all stuff to frame
    void addPaneltoFrame(Container container)
    {
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        container.add(this);
        this.setSize(288,512);
        JLabel label = new JLabel("Press ↑ to soar up,↓ to swoop, N for new game");
        container.add(label);

    }
}
