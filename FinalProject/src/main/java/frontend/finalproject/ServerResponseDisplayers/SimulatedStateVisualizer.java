package frontend.finalproject.ServerResponseDisplayers;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import frontend.finalproject.Controllers.HomeController;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SimulatedStateVisualizer implements IJsonVisualizer {
    public static final String SIMULATED_STATE = "SimulatedState";
    public static final String BTN_STYLE_CLASS = "formBtn";
    public static final String ALIGN_CENTER_STYLE_CLASS = "TreeBoxWrapper";
    public static final int HBOX_SPACING = 10;
    public static final int VBOX_SPACING = 20;
    public static final String TREE_VIEW_HEIGHT = "-fx-pref-height: 400";
    public static final String V_BOX_STATE_WRAPPER = "VBoxStateWrapper";
    public static final String ACTION_LABEL_STYLE_CLASS = "Separator_Text";
    public static final String EXECUTION_OUTCOME_JSON_KEY = "ExecutionOutcome";
    public static final String ACTION_DETAILS_JSON_KEY = "ActionDetails";
    public static final String ACTION_DESCRIPTION_JSON_KEY = "ActionDescription";
    private List<JsonElement> simulatedStates;
    private List<String> actionDescriptions;
    private int currentSimulatedStateIndex = 0;

    public SimulatedStateVisualizer(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        simulatedStates = new LinkedList<>();
        if (jsonElement.isJsonArray()) {
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                simulatedStates.add(element);
            }
        }
        getActionDescriptionsSequence(gson);
    }

    public List<String> getActionDescriptions() {
        return actionDescriptions;
    }

    private void getActionDescriptionsSequence(Gson gson) {
        actionDescriptions = new LinkedList<>();
        Response<String> execOutcome = AOSFacade.getInstance().sendRequest(new GetExecutionOutcomeRequestDTO(1));
        if(!execOutcome.hasErrorOccurred()){
            JsonElement execOutcomeJson = gson.fromJson(execOutcome.getValue(), JsonElement.class);
            execOutcomeJson = execOutcomeJson.getAsJsonObject().get(EXECUTION_OUTCOME_JSON_KEY);
            if(execOutcomeJson.isJsonArray()){
                for(JsonElement element : execOutcomeJson.getAsJsonArray()){
                    if(element.getAsJsonObject().has(ACTION_DETAILS_JSON_KEY)){
                        JsonElement actionDetails = element.getAsJsonObject().get(ACTION_DETAILS_JSON_KEY);
                        actionDescriptions.add(actionDetails.getAsJsonObject().get(ACTION_DESCRIPTION_JSON_KEY).getAsString());
                    }
                    else{
                        actionDescriptions.add("Initial State");
                    }
                }
            }
        }
    }

    @Override
    public Node displayJSON() {
        SimulatedStateNode simulatedStateNode = new SimulatedStateNode(simulatedStates,actionDescriptions);
        return simulatedStateNode;
    }


    public static class ChangedHighlightingTreeCell extends TreeCell<String> {

        public static final String HIGHLIGHT_CHANGES_NEXT = "highlight-changes-next";
        private static final String HIGHLIGHT_CHANGES_BACK = "highlight-changes-back";

        public ChangedHighlightingTreeCell(ObservableSet<TreeItem<String>> changes, boolean isNextBtn) {
            PseudoClass highlightChangeCSS = PseudoClass.getPseudoClass(isNextBtn ? HIGHLIGHT_CHANGES_NEXT : HIGHLIGHT_CHANGES_BACK);

            BooleanBinding shouldHighlight = Bindings.createBooleanBinding(() ->
                            changes.contains(getTreeItem()),
                    treeItemProperty(), changes);
            shouldHighlight.addListener((obs, didMatchSearch, nowMatchesSearch) ->
                    pseudoClassStateChanged(highlightChangeCSS, nowMatchesSearch));
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? null : item);
        }
    }

    public static class SimulatedStateNode extends Node{
        private VBox root;
        Node[] curState;
        HBox buttonContainer;
        Button display;

        private List<JsonElement> simulatedStates;
        private List<String> actionDescriptions;
        private int currentSimulatedStateIndex = 0;
        public SimulatedStateNode(List<JsonElement> simulatedStates, List<String> actionDescriptions) {
            super();
            this.simulatedStates = simulatedStates;
            this.actionDescriptions = actionDescriptions;

            root = new VBox();
            curState = new Node[]{new JsonTreeViewVisualizer(simulatedStates.size() == 0 ? "{}" :
                    simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON()};
            expandAllTreeItems(((TreeView<String>) curState[0]).getRoot());
            curState[0].setStyle(TREE_VIEW_HEIGHT);
            buttonContainer = new HBox();

            display = new Button("Display state");
            display.setOnAction(this::handleDisplayBtn);


            buttonContainer.getChildren().addAll(display);
            root.getChildren().addAll(curState[0], buttonContainer);


            // Adding style
            stylizeComponent();
        }

        public void handleDisplayBtn(ActionEvent event) {
            JsonElement currentState = simulatedStates.get(currentSimulatedStateIndex);
            currentState = currentState.getAsJsonObject().get(SIMULATED_STATE);
            Response<String> response = AOSFacade.getInstance().visualizeBeliefState(currentState.getAsJsonObject());
            String response_string = response.getValue();
            if(!response.hasErrorOccurred() && !response_string.isEmpty()){
                try {
                    Parent root = UtilsFXML.openNewWindow("display-belief-state.fxml");
                    Scene scene = new Scene(root, 640, 640);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Display Result");
                    stage.show();
                    TextArea label = (TextArea) scene.lookup("#displayArea");
                    label.setText(response_string);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        public void handleNextBtn(ActionEvent event){
            if (currentSimulatedStateIndex < simulatedStates.size() - 1) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex++;
//                ((Button)event.getSource()).setDisable(currentSimulatedStateIndex == simulatedStates.size() - 1);
//                Scene currentScene = ((Button) event.getSource()).getScene();
//                currentScene.lookup("#prevStateButton").setDisable(false);
                handleBrowseStateBtnClick(root, curState, prevState,true);
            }
        }

        public void handlePrevBtn(ActionEvent event){
            if (currentSimulatedStateIndex > 0) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex--;
//                ((Button)event.getSource()).setDisable(currentSimulatedStateIndex == 0);
//                Scene currentScene = ((Button) event.getSource()).getScene();
//                currentScene.lookup("#nextStateButton").setDisable(false);
                handleBrowseStateBtnClick(root, curState, prevState,false);
            }
        }

        private void stylizeComponent() {
            display.getStyleClass().add(BTN_STYLE_CLASS);
            buttonContainer.getStyleClass().add(ALIGN_CENTER_STYLE_CLASS);
            root.getStyleClass().add(V_BOX_STATE_WRAPPER);
            buttonContainer.setSpacing(HBOX_SPACING);
            root.setSpacing(VBOX_SPACING);
        }

        private void expandAllTreeItems(TreeItem<?> treeItem) {
            treeItem.setExpanded(true);
            for (TreeItem<?> child : treeItem.getChildren()) {
                child.setExpanded(true);
            }
        }

        private void handleBrowseStateBtnClick(VBox vBox, Node[] curState, JsonElement prevState, boolean isNextBtn) {
            JsonElement nextState = simulatedStates.get(currentSimulatedStateIndex);
            nextState = nextState.getAsJsonObject().get(SIMULATED_STATE);

            vBox.getChildren().remove(curState[0]);
            curState[0] = new JsonTreeViewVisualizer(simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON();
            curState[0].setStyle(SimulatedStateVisualizer.TREE_VIEW_HEIGHT);
            expandAllTreeItems(((TreeView<String>) curState[0]).getRoot());
            highlightChangedVariablesInState(prevState, nextState, (TreeView<String>) curState[0],isNextBtn);
            vBox.getChildren().add(0, curState[0]);
        }

        private void highlightChangedVariablesInState(JsonElement oldState, JsonElement newState, TreeView<String> treeView, boolean isNextBtn) {
            ObservableSet<TreeItem<String>> changes = FXCollections.observableSet(new HashSet<>());

            treeView.setCellFactory(tv -> new ChangedHighlightingTreeCell(changes,isNextBtn));

            if (oldState.isJsonObject() && newState.isJsonObject()) {
                for (String key : oldState.getAsJsonObject().keySet()) {
                    if (newState.getAsJsonObject().has(key)) {
                        JsonElement oldVal = oldState.getAsJsonObject().get(key);
                        JsonElement newVal = newState.getAsJsonObject().get(key);
                        if (!oldVal.equals(newVal)) {
                            if(oldVal.isJsonArray()){
                                for(int i=0;i<oldVal.getAsJsonArray().size();i++){
                                    if(!oldVal.getAsJsonArray().get(i).equals(newVal.getAsJsonArray().get(i))){
                                        findAndAddTreeItem(key + "[" + i + "]", treeView.getRoot(), changes);
                                    }
                                }
                            }
                            findAndAddTreeItem(key, treeView.getRoot(), changes);
                        }
                    }
                }
            }

        }

        private void findAndAddTreeItem(String changedKey, TreeItem<String> root, ObservableSet<TreeItem<String>> changes) {
            if (root.getValue()!= null && root.getValue().startsWith(changedKey)) {
                changes.add(root);
                if(root.getValue().contains("{}")){
                    changes.addAll(root.getChildren());
                }
            } else {
                for (TreeItem<String> child : root.getChildren()) {
                    findAndAddTreeItem(changedKey, child, changes);
                }
            }
        }

        public VBox getRoot() {
            return root;
        }
    }
}
