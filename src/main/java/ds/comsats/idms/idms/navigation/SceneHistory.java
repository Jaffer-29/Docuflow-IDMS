package ds.comsats.idms.idms.navigation;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public class SceneHistory {

    private final Stack<Scene> backStack = new Stack<>();
    private final Stack<Scene> forwardStack = new Stack<>();

    private final Stage stage;

    public SceneHistory(Stage stage){
        this.stage = stage;
    }


    public void push(Scene newScene){

        if(stage.getScene() != null) {
            backStack.push(stage.getScene());
        }
        forwardStack.clear();
        stage.setScene(newScene);
    }

    public void back(){
        if(!backStack.isEmpty()){
            forwardStack.push(stage.getScene());
            Scene previous = backStack.pop();
            stage.setScene(previous);
        }
    }

}