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

    Font font1;

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


    //constructor loads images, and get references from engine class and list of objects for drawing
    GameCanvas()
    {
        loadImages();
        setIndex();
        setInitBirdImg();
        setFocusable(true);
        FlappyBirdEngine gameEngine = new FlappyBirdEngine(this);
        this.drawables = gameEngine.getDrawables();

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

    //set bird image depending on action
    private void setBirdImage(Image currentBirdImage){birdImage = currentBirdImage;}

    //set initial bird image
    public void setInitBirdImg(){birdImage = birds[5];}

    //get images from another package
    private void loadImages()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird - 25.png"));
            Image currentBirdImage = img;
            birds[0] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird - 20.png"));
            currentBirdImage = img;
            birds[1] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird - 15.png"));
            currentBirdImage = img;
            birds[2] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird - 10.png"));
            currentBirdImage = img;
            birds[3] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird - 5.png"));
            currentBirdImage = img;
            birds[4] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird.png"));
            currentBirdImage = img;
            birds[5] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird + 5.png"));
            currentBirdImage = img;
            birds[6] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird + 10.png"));
            currentBirdImage = img;
            birds[7] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird + 15.png"));
            currentBirdImage = img;
            birds[8] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird + 20.png"));
            currentBirdImage = img;
            birds[9] = currentBirdImage;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bird + 25.png"));
            currentBirdImage = img;
            birds[10] = currentBirdImage;

            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_bg.png"));
            backGroundImage = img;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_fg.png"));
            foreGroundImage = img;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_pipeUp.png"));
            pipeUpImage = img;
            img = ImageIO.read(getClass().getResource("/resources/images/flappy_bird_pipeBottom.png"));
            pipeDownImage = img;
        }catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    //bird soaring up
    public void birdSoar()
    {
        setBirdImage(birds[index]);
        index--;
        if(index == 0)
        {
            index++;
        }

    }

    //bird swooping down
    public void birdSwoop()
    {
        setBirdImage(birds[index]);
        index++;
        if(index == 10)
        {
            index = index - 2;
        }
    }


    //draw all stuff
    public void paintComponent(Graphics g) {

        if (inGame) {

            super.paintComponent(g);

            //Draw background
            g.drawImage(backGroundImage, 0, 0, this);

            //Draw bird
            g.drawImage(birdImage, birdPositionX, birdPositionY, this);

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
            g.setFont(new Font("Scores", Font.BOLD ,16));
            g.drawString("Scores: " + scoreCounter, BIRD_POSITION_X / 2, backGroundImage.getHeight(this) -
                    foreGroundImage.getHeight(this) / 2 - 10);

            requestFocus();

        }
        /**
        else
        {
          scoreWindow();
        }
         */
    }
    /**
    private void scoreWindow()
    {
        JFrame fr = new JFrame("Game over");
        JPanel panel = new JPanel(new FlowLayout());
        fr.setContentPane(panel);


        JLabel name = new JLabel("Enter your name");
        JTextField textField = new JTextField(20);
        JButton confirm = new JButton("Enter");
        panel.add(name);
        panel.add(textField);
        panel.add(confirm);
        fr.setBounds(100,100,288,130);
        //fr.setFocusable(false);
        fr.setResizable(false);
        fr.setVisible(true);
    }
     */

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
