/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewerproject;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author cille
 */
public class Slideshow implements Runnable {
    private List<Image> images;
    private List<String> names;
    private ImageView imageView;
    private Label label;
    private long delay = 2;
    private int index = 0;
    //Need list filenames
    
    public Slideshow(ImageView imageView, Label label, List<Image> images, List<String> names)
    {
        this.imageView = imageView;
        this.label = label;
        this.images = images;
        this.names = names;
    }

    @Override
    public void run() {
        if(!images.isEmpty())
        {
            try{
                while(!Thread.currentThread().isInterrupted())
                {
                    Platform.runLater(() -> {
                    imageView.setImage(images.get(index));
                    label.setText(names.get(index));
                    

                    });
                    index = (index +1) % images.size();
                    TimeUnit.SECONDS.sleep(delay);

                }
            }
            catch(InterruptedException ex){
                label.setText("Slideshow was interrupted.");
                System.out.println("Slideshow interrupted");
            }

        }

//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
