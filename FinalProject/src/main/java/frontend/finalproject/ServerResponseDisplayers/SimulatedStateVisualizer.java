package frontend.finalproject.ServerResponseDisplayers;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.Constants;
import backend.finalproject.IAOSFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Response;
import utils.ScriptResponse;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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
    private IAOSFacade facade;

    public SimulatedStateVisualizer(String json, IAOSFacade facade) {
        this.facade = facade;
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
        Response<String> execOutcome = this.facade.sendRequest(new GetExecutionOutcomeRequestDTO(1,""));
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
        private boolean isNextBtn;
        private ObservableSet<TreeItem<String>> changes;
        private PseudoClass highlightChangeCSS;

        public ChangedHighlightingTreeCell(ObservableSet<TreeItem<String>> changes, boolean isNextBtn) {
            this.isNextBtn = isNextBtn;
            this.changes = changes;
            this.highlightChangeCSS = PseudoClass.getPseudoClass(isNextBtn ? HIGHLIGHT_CHANGES_NEXT : HIGHLIGHT_CHANGES_BACK);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                pseudoClassStateChanged(this.highlightChangeCSS, false); // Reset the highlighting
            } else {
                setText(item);
                setGraphic(getTreeItem().getGraphic());
                pseudoClassStateChanged(this.highlightChangeCSS, isHighlighted()); // Apply highlighting
            }
        }

        private boolean isHighlighted(){
            return changes.contains(getTreeItem());
        }
    }

    public static class SimulatedStateNode extends Node{
        public static final int STATE_VIEW_INDEX = 1;
        private final HBox tableViewStateViewContainer;
        private VBox root;
        Node[] curState;
        HBox buttonContainer;
        Button display;

        private List<JsonElement> simulatedStates;
        private List<String> actionDescriptions;
        private int currentSimulatedStateIndex = 0;
        private boolean isDisplayMode = false;
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
            display.setOnAction(event -> handleDisplayBtn(event, false));

            tableViewStateViewContainer = new HBox();

            tableViewStateViewContainer.getChildren().addAll(curState[0]);

            buttonContainer.getChildren().addAll(display);
            root.getChildren().addAll(tableViewStateViewContainer, buttonContainer);


            // Adding style
            stylizeComponent();
        }

        public void handleDisplayBtn(ActionEvent event, boolean isSourceFromBrowseBtn) {
            clearCurrentStateView();

            if (!isSourceFromBrowseBtn)
                handleDisplayModeChange();

            if (!isDisplayMode)
                return;

            JsonElement currentState = simulatedStates.get(currentSimulatedStateIndex);
            currentState = currentState.getAsJsonObject().get(SIMULATED_STATE);
            Response<ScriptResponse> response = AOSFacade.getInstance().visualizeBeliefState(currentState.getAsJsonObject());
            if (!response.hasErrorOccurred()) {
                String response_string = response.getValue().getOutput();
                boolean isSaveFig = response.getValue().getSaveFig();
                if (isSaveFig) {
                    try {
                        File image = new File(Constants.SINGLE_STATE_IMAGE_FNAME);
                        ImageView imageView = new ImageView();
                        imageView.setImage(new Image(image.toURI().toString()));
                        imageView.setFitHeight(400);
                        imageView.setFitWidth(400);
                        ScrollPane scrollContainer = new ScrollPane(imageView);
                        scrollContainer.setPrefHeight(450);
                        tableViewStateViewContainer.getChildren().add(scrollContainer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!response_string.isEmpty()) {
                        TextArea text = new TextArea();
                        text.setText(response_string);
                        text.setMinHeight(40);
                        text.getStyleClass().add("TextAreaWithMargin");
                        text.setEditable(false);
                        tableViewStateViewContainer.getChildren().add(text);
                    }
                }

            }
        }

        private void clearCurrentStateView() {
            if(isDisplayMode)
                tableViewStateViewContainer.getChildren().remove(STATE_VIEW_INDEX);
        }

        private void handleDisplayModeChange() {
            isDisplayMode = !isDisplayMode;
            if(isDisplayMode){
                display.setText("Hide display state");
            }
            else{
                display.setText("Display state");
            }
        }

        public void handleNextBtn(ActionEvent event){
            if (currentSimulatedStateIndex < simulatedStates.size() - 1) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex++;
                handleBrowseStateBtnClick(curState, prevState,true);
            }
        }

        public void handlePrevBtn(ActionEvent event){
            if (currentSimulatedStateIndex > 0) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex--;
                handleBrowseStateBtnClick(curState, prevState,false);
            }
        }

        private void stylizeComponent() {
            display.getStyleClass().add(BTN_STYLE_CLASS);
            buttonContainer.getStyleClass().add(ALIGN_CENTER_STYLE_CLASS);
            root.getStyleClass().add(V_BOX_STATE_WRAPPER);
            buttonContainer.setSpacing(HBOX_SPACING);
            root.setSpacing(VBOX_SPACING);
            tableViewStateViewContainer.getStyleClass().add(ALIGN_CENTER_STYLE_CLASS);
            tableViewStateViewContainer.setSpacing(VBOX_SPACING);
        }

        private void expandAllTreeItems(TreeItem<?> treeItem) {
            treeItem.setExpanded(true);
            for (TreeItem<?> child : treeItem.getChildren()) {
                child.setExpanded(true);
            }
        }

        private void handleBrowseStateBtnClick(Node[] curState, JsonElement prevState, boolean isNextBtn) {
            JsonElement nextState = simulatedStates.get(currentSimulatedStateIndex);
            nextState = nextState.getAsJsonObject().get(SIMULATED_STATE);

            tableViewStateViewContainer.getChildren().remove(curState[0]);
            curState[0] = new JsonTreeViewVisualizer(simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON();
            curState[0].setStyle(SimulatedStateVisualizer.TREE_VIEW_HEIGHT);
            expandAllTreeItems(((TreeView<String>) curState[0]).getRoot());
            highlightChangedVariablesInState(prevState, nextState, (TreeView<String>) curState[0],isNextBtn);
            tableViewStateViewContainer.getChildren().add(0, curState[0]);

            handleDisplayBtn(null,true);
        }

        public static ObservableSet<TreeItem<String>> highlightChangedVariablesInState(JsonElement oldState, JsonElement newState, TreeView<String> treeView, boolean isNextBtn) {
            ObservableSet<TreeItem<String>> changes = FXCollections.observableSet(new HashSet<>());

            treeView.setCellFactory(tv -> new ChangedHighlightingTreeCell(changes,isNextBtn));
            TreeItem<String> simStateRoot = findSimStateRootInTreeView(treeView);

            if (oldState.isJsonObject() && newState.isJsonObject()) {
                handleChanges(oldState, newState, changes, simStateRoot);
            }
            return changes;
        }

        private static void handleChanges(JsonElement oldState, JsonElement newState, ObservableSet<TreeItem<String>> changes, TreeItem<String> simStateRoot) {
            for (String key : oldState.getAsJsonObject().keySet()) {
                if (newState.getAsJsonObject().has(key)) {
                    JsonElement oldVal = oldState.getAsJsonObject().get(key);
                    JsonElement newVal = newState.getAsJsonObject().get(key);
                    if (!oldVal.equals(newVal)) {
                        for(TreeItem<String> item:simStateRoot.getChildren()){
                            if(item.getValue().startsWith(key)){
                                dfs(item,changes,oldVal,newVal);
                            }
                        }
                    }
                }
            }
        }

        private static TreeItem<String> findSimStateRootInTreeView(TreeView<String> treeView) {
            for(TreeItem<String> item: treeView.getRoot().getChildren()){
                if(item.getValue().startsWith(SIMULATED_STATE)){
                    return item;
                }
            }
            return null;
        }

        private static void dfs(TreeItem<String> item, ObservableSet<TreeItem<String>> changes, JsonElement oldState, JsonElement newState) {
            changes.add(item);

            if (item.getChildren().size() == 0)
                return;

            if (oldState.isJsonObject() && newState.isJsonObject()) {
                handleChanges(oldState, newState, changes, item);
            }
            else if (oldState.isJsonArray() && newState.isJsonArray()) {
                for(int i=0;i<oldState.getAsJsonArray().size();i++){
                    JsonElement oldVal = oldState.getAsJsonArray().get(i);
                    JsonElement newVal = newState.getAsJsonArray().get(i);
                    if(!oldVal.equals(newVal)){
                        for(TreeItem<String> child:item.getChildren()){
                            if(child.getValue().contains("["+i+"]")){
                                dfs(child,changes,oldVal,newVal);
                            }
                        }
                    }
                }
            }
        }

        public VBox getRoot() {
            return root;
        }
    }
}
