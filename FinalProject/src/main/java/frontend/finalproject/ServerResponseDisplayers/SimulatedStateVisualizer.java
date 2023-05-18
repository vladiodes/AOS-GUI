package frontend.finalproject.ServerResponseDisplayers;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import backend.finalproject.AOSFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Response;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SimulatedStateVisualizer implements IJsonVisualizer {
    public static final String SIMULATED_STATE = "SimulatedState";
    public static final String BTN_STYLE_CLASS = "formBtn";
    public static final String ALIGN_CENTER_STYLE_CLASS = "TreeBoxWrapper";
    public static final int HBOX_SPACING = 10;
    public static final int VBOX_SPACING = 20;
    public static final String TREE_VIEW_HEIGHT = "-fx-pref-height: 700";
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
        VBox vBox = new VBox();
        final Node[] curState = {new JsonTreeViewVisualizer(simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON()};
        expandAllTreeItems(((TreeView<String>) curState[0]).getRoot());
        curState[0].setStyle(TREE_VIEW_HEIGHT);
        HBox hBox = new HBox();
        Button prev = new Button();
        prev.setText("Previous State");
        Button next = new Button();
        next.setText("Next State");
        Button display = new Button("Display state");
        
        Label prevExecAction = new Label("Previously executed action: " + actionDescriptions.get(currentSimulatedStateIndex));
        String nextAction = currentSimulatedStateIndex + 1 < actionDescriptions.size() ?
                actionDescriptions.get(currentSimulatedStateIndex + 1) :
                "No more actions";
        Label nextActionToExec = new Label("Next action to execute: " + nextAction);

        hBox.getChildren().addAll(prev, display,next);
        vBox.getChildren().addAll(curState[0],prevExecAction,nextActionToExec, hBox);

        prev.setOnAction(e -> {
            if (currentSimulatedStateIndex > 0) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex--;
                handleBrowseStateBtnClick(vBox, curState, prevState,false,prevExecAction, nextActionToExec);
            }
        });

        next.setOnAction(e -> {
            if (currentSimulatedStateIndex < simulatedStates.size() - 1) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex++;
                handleBrowseStateBtnClick(vBox, curState, prevState,true,prevExecAction,nextActionToExec);
            }
        });

        // Adding style
        stylizeComponent(vBox, hBox, prev, next, display,prevExecAction,nextActionToExec);
        return vBox;
    }

    private void handleBrowseStateBtnClick(VBox vBox, Node[] curState, JsonElement prevState, boolean isNextBtn, Label prevExecAction, Label nextActionToExec) {
        JsonElement nextState = simulatedStates.get(currentSimulatedStateIndex);
        nextState = nextState.getAsJsonObject().get(SIMULATED_STATE);

        vBox.getChildren().remove(curState[0]);
        curState[0] = new JsonTreeViewVisualizer(simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON();
        curState[0].setStyle(SimulatedStateVisualizer.TREE_VIEW_HEIGHT);
        expandAllTreeItems(((TreeView<String>) curState[0]).getRoot());
        highlightChangedVariablesInState(prevState, nextState, (TreeView<String>) curState[0],isNextBtn);
        prevExecAction.setText("Previously executed action: " + actionDescriptions.get(currentSimulatedStateIndex));
        String nextAction = currentSimulatedStateIndex + 1 < actionDescriptions.size() ?
                actionDescriptions.get(currentSimulatedStateIndex + 1) :
                "No more actions";
        nextActionToExec.setText("Next action to execute: " + nextAction);
        vBox.getChildren().add(0, curState[0]);
    }

    private void expandAllTreeItems(TreeItem<?> treeItem) {
        treeItem.setExpanded(true);
        for (TreeItem<?> child : treeItem.getChildren()) {
            child.setExpanded(true);
        }
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

    private static void stylizeComponent(VBox vBox, HBox hBox, Button prev, Button next, Button display, Label executedAction, Label nextActionToExec) {
        prev.getStyleClass().add(BTN_STYLE_CLASS);
        next.getStyleClass().add(BTN_STYLE_CLASS);
        display.getStyleClass().add(BTN_STYLE_CLASS);
        hBox.getStyleClass().add(ALIGN_CENTER_STYLE_CLASS);
        vBox.getStyleClass().add(V_BOX_STATE_WRAPPER);
        hBox.setSpacing(HBOX_SPACING);
        vBox.setSpacing(VBOX_SPACING);
        executedAction.getStyleClass().add(ACTION_LABEL_STYLE_CLASS);
        nextActionToExec.getStyleClass().add(ACTION_LABEL_STYLE_CLASS);
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
}
