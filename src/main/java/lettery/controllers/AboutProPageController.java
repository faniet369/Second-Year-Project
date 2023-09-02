package lettery.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutProPageController extends BackBtnController{
    @FXML private ImageView picMe;
    @FXML private Button backBtn;

    @FXML public void initialize() {
        picMe.setImage(new Image("/images/suthima.jpg"));
    }
}
