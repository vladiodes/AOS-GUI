package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private List<JsonElement> simulatedStates;
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
        hBox.getChildren().addAll(prev, display,next);
        vBox.getChildren().addAll(curState[0], hBox);

        prev.setOnAction(e -> {
            if (currentSimulatedStateIndex > 0) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex--;
                handleBrowseStateBtnClick(vBox, curState, prevState,false);
            }
        });

        next.setOnAction(e -> {
            if (currentSimulatedStateIndex < simulatedStates.size() - 1) {
                JsonElement prevState = simulatedStates.get(currentSimulatedStateIndex);
                prevState = prevState.getAsJsonObject().get(SIMULATED_STATE);
                currentSimulatedStateIndex++;
                handleBrowseStateBtnClick(vBox, curState, prevState,true);
            }
        });

        // Adding style
        stylizeComponent(vBox, hBox, prev, next, display);
        return vBox;
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

    private static void stylizeComponent(VBox vBox, HBox hBox, Button prev, Button next, Button display) {
        prev.getStyleClass().add(BTN_STYLE_CLASS);
        next.getStyleClass().add(BTN_STYLE_CLASS);
        display.getStyleClass().add(BTN_STYLE_CLASS);
        hBox.getStyleClass().add(ALIGN_CENTER_STYLE_CLASS);
        vBox.getStyleClass().add(V_BOX_STATE_WRAPPER);
        hBox.setSpacing(HBOX_SPACING);
        vBox.setSpacing(VBOX_SPACING);
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
