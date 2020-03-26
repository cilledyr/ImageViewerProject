package imageviewerproject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable
{
    
    private final List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    private final ExecutorService executor;
    private final List<String> names = new ArrayList<>();
//NEd list String filenames
    @FXML
    Parent root;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnNext;
    
    @FXML
    private Button btnSlideshow;
    
    @FXML
    private Button btnStop;
    
    @FXML
    private Label fileName;

    @FXML
    private ImageView imageView;

    //need label filenames
    
    public FXMLDocumentController() {
        this.executor = Executors.newSingleThreadExecutor();
       
    }

    private void handleBtnLoadAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", 
            "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));        
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
                
        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                //addfilename
                names.add(f.getName());
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    private void handleBtnPreviousAction(ActionEvent event)
    {
        if (!images.isEmpty())
        {
            currentImageIndex = 
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    private void handleBtnNextAction(ActionEvent event)
    {
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void displayImage()
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
            fileName.setText(names.get(currentImageIndex));
        }
    }
    
    private void handleBtnSlideshowAction(ActionEvent event)
    {
        if (!images.isEmpty())
        {
            displayImage();
            Runnable taskSlideshow = new Slideshow(imageView, fileName, images, names);//need to pass it the elist of filenames.
            
            
            // Executors typically manage a pool of threads, so we don't have to 
            // create new threads manually. In this example we use an executor with
            // a thread pool of size one.
            
            executor.submit(taskSlideshow);
        }
    }
    
    private void handleBtnStopAction(ActionEvent event)
    {
        executor.shutdownNow();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        btnLoad.setOnAction((ActionEvent event) ->
        {
            handleBtnLoadAction(event);
        });

        btnPrevious.setOnAction((ActionEvent event) ->
        {
            handleBtnPreviousAction(event);
        });
        
        btnNext.setOnAction((ActionEvent event) ->
        {
            handleBtnNextAction(event);
        });
        
        btnSlideshow.setOnAction((ActionEvent event) ->
        {
            handleBtnSlideshowAction(event);
        });
        
        btnStop.setOnAction((ActionEvent event) ->
        {
            handleBtnStopAction(event);
        });
    }
    
}


