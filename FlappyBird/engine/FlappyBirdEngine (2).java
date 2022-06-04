package engine;

import screens.GameCanvas;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlappyBirdEngine implements  ActionListener {

    //reference to GameCanvas class
    private GameCanvas cvs;

    //List for creating pipe objects
    private List<Drawable> drawables;

    private int acceleration = 0;

    //Constructor with references to GameCanvas class, timer, KeyListener, and creator of first pipes
    public FlappyBirdEngine(GameCanvas cvs)
    {
        this.cvs = cvs;
        setTimer();
        cvs.addKeyListener(new FieldKeyListener());
        createFirstObject();
        createScoreFile();
        soaring();
    }


    //actions are performing here
    @Override
    public void actionPerformed(ActionEvent e)
    {
        fall();
        animate();
        checkTouch();
        getScoreCounter();
    }

    //check of file existence and create score file
    private void createScoreFile()
    {
        File scores = new File("scores.txt");
        try{

            scores.createNewFile();

        }catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }
    //create new timer
    private void setTimer()
    {
        Timer timer = new Timer(30, this);
        timer.start();
    }

    //make bird soaring all the time
    private void soaring()
    {
        Timer soarTimer = new Timer(30, e -> cvs.fly());
        soarTimer.start();
    }


    //make bird to fall
    private void fall()
    {
        int birdPositionY = cvs.getBirdPositionY();
        int gravity = 1;
        acceleration = acceleration + gravity;
        cvs.setBirdPositionY(birdPositionY + acceleration);
    }

    //create first pipes
    private void createFirstObject()
    {
        drawables = new ArrayList<>(2);
        drawables.add(new Drawable(cvs.getPipeX(), cvs.getPipeY()));
    }

    //get drawables list and then pass reference to GameCanvas class list
    public List<Drawable> getDrawables()
        {
            return drawables;
        }


    //count pipes passed
    private void getScoreCounter()
    {
        for(Drawable drawable : drawables)
        {
            if(drawable.getX()+ cvs.pipeUpImage.getWidth(cvs) == cvs.getBirdPositionX() && cvs.getInGame())
                cvs.setScoreCounter(cvs.getScoreCounter() + 1);
        }
    }

    //create animation of objects, creating new random pipes

    private void animate()
    {
        for(Drawable drawable : new ArrayList<>(drawables))
        {
            drawable.update();
            if(drawable.getX() == 128)
            {
                drawables.add(new Drawable(cvs.getPipeX(), (int)(Math.random() *
                        (cvs.pipeUpImage.getHeight(null) -
                        ((cvs.pipeUpImage.getHeight(null) * 0.2))) - (cvs.pipeUpImage.getHeight(null) -
                        (cvs.pipeUpImage.getHeight(null) * 0.2)))));
            }
            if(drawable.getX() <= - cvs.pipeDownImage.getWidth(null))
            {
                drawables.remove(drawable);
            }
        }
        cvs.repaint();
    }



    //check if bird touched any obstacles
    private void checkTouch()
    {
        for (Drawable drawable : drawables)
        {
                //foreground touch
                if (cvs.getBirdPositionY() + cvs.getBirdSubImage().getHeight(null) >=
                        cvs.backGroundImage.getHeight(null) - cvs.foreGroundImage.getHeight(null)) {
                    cvs.setInGame(false);
                    //timer.stop();
                }
                //top pipe front touch
                else if (drawable.getX() == cvs.getBirdPositionX() + cvs.getBirdSubImage().getWidth(null) &&
                        cvs.getBirdPositionY() <= drawable.getY() +
                                cvs.pipeUpImage.getHeight(null)) {
                    cvs.setInGame(false);
                    //timer.stop();
                }
                //bottom pipe front touch
                else if (drawable.getX() == cvs.getBirdPositionX() + cvs.getBirdSubImage().getWidth(null) &&
                        cvs.getBirdPositionY() + cvs.getBirdSubImage().getHeight(null) >= drawable.getY() +
                                cvs.pipeUpImage.getHeight(null) + cvs.getGAP()) {
                    cvs.setInGame(false);
                    //timer.stop();
                }
                //top pipe lower touch
                else if ((drawable.getX() <= cvs.getBirdPositionX() + cvs.getBirdSubImage().getWidth(null) &&
                        (drawable.getX() + cvs.pipeUpImage.getWidth(null) >= cvs.getBirdPositionX())) &&
                        ((drawable.getY() + cvs.pipeUpImage.getHeight(null)) >=
                                cvs.getBirdPositionY())) {
                    cvs.setInGame(false);
                    //timer.stop();
                }
                //bottom pipe upper touch
                else if ((drawable.getX() <= cvs.getBirdPositionX() + cvs.getBirdSubImage().getWidth(null) &&
                        (drawable.getX() + cvs.pipeDownImage.getWidth(null) > cvs.getBirdPositionX())) &&
                        ((drawable.getY() + cvs.pipeUpImage.getHeight(null) + cvs.getGAP()) <=
                                (cvs.getBirdPositionY() + cvs.getBirdSubImage().getHeight(null)))) {
                    cvs.setInGame(false);
                    //timer.stop();
                }
                //upper border touch (throw off by 10 px)
                else if (cvs.getBirdPositionY() <= (cvs.backGroundImage.getHeight(null) -
                        cvs.backGroundImage.getHeight(null))) {
                    cvs.setBirdPositionY(cvs.getBirdPositionY() + 5);
                }
        }
    }

    //key listener for any actions
    public class FieldKeyListener extends KeyAdapter
    {

        @Override
        public void keyPressed(KeyEvent e)
        {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_UP)
            {
                //cvs.birdSoar();
                cvs.setBirdPositionY(cvs.getBirdPositionY() - 1);
                acceleration = -7;
            }
            if(key == KeyEvent.VK_DOWN)
            {
                //cvs.birdSwoop();
                cvs.setBirdPositionY(cvs.getBirdPositionY() + 5);
            }
            if(key == KeyEvent.VK_N)
            {
                drawables.removeAll(drawables);
                drawables.add(new Drawable(cvs.getPipeX(), cvs.getPipeY()));
                cvs.setInGame(true);
                cvs.setBirdPositionY(100);
                cvs.setScoreCounter(0);
                acceleration = 0;
            }
        }
    }

    //class where get and set and process all pipes
    public static class Drawable
    {
        private int x;
        private int y;
        Drawable(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public int getX()
        {
            return x;
        }
        public int getY()
        {
            return y;
        }
        void update() {
            x -= 2;
        }
    }
}