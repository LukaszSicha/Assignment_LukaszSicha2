package JavaClasses;

import java.io.*;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import JavaClasses.MyListOfObjects;
import JavaClasses.Agent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginAgent {
    MyListOfObjects agents = new MyListOfObjects();

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField firstName;
    @FXML private TextField surename;
    @FXML private TextField email;
    @FXML private TextField userType;
    @FXML private TextArea txtAreaFeedback;




    public void handleLoginBtn(ActionEvent e) throws Exception {
        if(username.getText().length()<4 || password.getText().length()<4 ){
            txtAreaFeedback.setText("Username and Password need to be 4 characters or more");
        }
        else if(login(username.getText(),password.getText())){
            txtAreaFeedback.setText("Successful Login");
            Main.set_pane(7);
        }
        else {
            txtAreaFeedback.setText("Un-Successful Login");
            password.clear();
        }
    }

    private boolean login(String username, String password) {
        XStream xstream = new XStream(new DomDriver());
        try {
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("agents.xml"));
            agents = (MyListOfObjects) is.readObject();
            is.close();
        }
        catch(FileNotFoundException e) {
            agents =  new MyListOfObjects();
            txtAreaFeedback.setText("Password File not located\n" + e);
            return false;

        }
        catch (Exception e) {
            txtAreaFeedback.setText("Error accessing Password File\n" + e);
            return false;
        }

        for (int i = 0; i < agents.size(); i++) {
            Agent agentLogin = (Agent) agents.get(i);
            if (agentLogin.getUsername().contains(username) && agentLogin.getPassword().contains(password))
                return true;
        }
        return false;
    }

    public void handleButtonBack(ActionEvent e) throws Exception {
        Main.set_pane(0);
    }


}
