package GUI;

import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyIHeap;
import Collection.Stack.MyIStack;
import Controller.Controller;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Collection.ToySemaphore.MyIToySemaphoreTable;
import Collection.Tuple;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunProgramController implements Initializable {
    private Controller controller;

    ProgramState temp;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapValueColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> fileNameColumn;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolTableValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, Tuple<Integer, List<Integer>, Integer>>> toySemaphoreTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Tuple<Integer, List<Integer>, Integer>>, Integer> indexToySemaphoreColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Tuple<Integer, List<Integer>, Integer>>, Integer> valueToySemaphoreColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Tuple<Integer, List<Integer>, Integer>>, List<Integer>> listToySemaphoreColumn;


    @FXML
    private ListView<Value> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private ListView<String> fileTableView;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private Button executeOneStepButton;

    public void setController(Controller controller){
        this.controller = controller;
        populateProgramStateIdentifiers();
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList) {
        return programStateList.stream().map(ProgramState::getId).collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getRepository().getProgramStateList().stream().toList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));

        numberOfProgramStatesTextField.setText("" + programStates.size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()+""));

        symbolTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()+""));
        listToySemaphoreColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getSecond()));
        indexToySemaphoreColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueToySemaphoreColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().getFirst() - p.getValue().getValue().getThird()).asObject());
        listToySemaphoreColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getSecond()));
        programStateListView.setOnMouseClicked(mouseEvent -> {
            try {
                temp = getCurrentProgramState();
                changeProgramState(getCurrentProgramState());
                //controller.executeOneStep();
            } catch (ToyLanguageInterpreterException e) {
                throw new RuntimeException(e);
            }
        });

        executeOneStepButton.setOnAction(actionEvent -> {
            executeOneStep();
        });
    }

    public void executeOneStep() {
        try {
            //ProgramState currentProgramState = getCurrentProgramState();
            //if (currentProgramState != null) {
            //    changeProgramState(currentProgramState);
            //}
            controller.executeOneStep();
            populateProgramStateIdentifiers();
            if (temp != null) {
                changeProgramState(temp);
            }
            //populateProgramStateIdentifiers();
            temp=getCurrentProgramState();
            //ProgramState currentProgramState = getCurrentProgramState();
            //if (currentProgramState != null) {
            //    changeProgramState(currentProgramState);
            //}
            populateProgramStateIdentifiers();
        } catch (ToyLanguageInterpreterException | IOException | InterruptedException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
    private void populateToySemaphoreTableView() {
        ProgramState programState = getCurrentProgramState();
        MyIToySemaphoreTable semaphoreTable = Objects.requireNonNull(programState).getToySemaphoreTable();
        List<Map.Entry<Integer, Tuple<Integer, List<Integer>, Integer>>> toySemaphoreList = new ArrayList<>();
        for (Map.Entry<Integer, Tuple<Integer, List<Integer>, Integer>> entry: semaphoreTable.getToySemaphoreTable().entrySet()) {
            toySemaphoreList.add(entry);
        }
        toySemaphoreTableView.setItems(FXCollections.observableArrayList(toySemaphoreList));
        toySemaphoreTableView.refresh();
    }

    private void changeProgramState(ProgramState currentProgramState) throws ToyLanguageInterpreterException {
        if(currentProgramState == null)
            return;
        populateExecutionStack(currentProgramState);
        populateSymbolTable(currentProgramState);
        populateOutput(currentProgramState);
        populateFileTable(currentProgramState);
        populateHeapTable(currentProgramState);
        populateToySemaphoreTableView();
    }

    private ProgramState getCurrentProgramState(){
        if(programStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;
        int currentId = programStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepository().getProgramStateWithId(currentId);
    }

    private void populateHeapTable(ProgramState currentProgramState) throws ToyLanguageInterpreterException {
        MyIHeap<Integer, Value> heapTable = currentProgramState.getHeap();

        List<Map.Entry<Integer, Value>> heapTableList = new ArrayList<>();

        for(Map.Entry<Integer, Value> entry : heapTable.getAll())
            heapTableList.add(entry);
        heapTableView.refresh();
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateFileTable(ProgramState currentProgramState) {
        MyIDictionary<StringValue, BufferedReader> fileTable = currentProgramState.getFileTable();
        List<String> fileTableList = new ArrayList<>();
        //Random random = new Random();
        fileTable.getValues().stream().toList().forEach(p->fileTableList.add(fileTable.getKeyByValue(p).toString()));
        fileTableView.refresh();
        fileTableView.setItems(FXCollections.observableList(fileTableList));
        fileTableView.refresh();
    }

    private void populateOutput(ProgramState currentProgramState) {
        //List<Value> output = new ArrayList<>(currentProgramState.getOutputList().stream().toList());
        List<Value> temp = new ArrayList<>();
        for(Value entry : currentProgramState.getOutputList().getAll())
            temp.add(entry);
        /*List<IntValue> output2 = new ArrayList<>();
        for (Value value : output) {
            output2.add((IntValue) value);
        }
        List<Integer> output3 = new ArrayList<>();
        for(IntValue value :output2){
            output3.add(value.getVal());
        }*/
        outputListView.refresh();
        outputListView.setItems(FXCollections.observableList(temp));
        outputListView.refresh();
    }

    private void populateSymbolTable(ProgramState currentProgramState) {
        MyIDictionary<String, Value> symbolTable = currentProgramState.getSymbolTable();
        //MyIDictionary<String, Value> symbolTable2 = new MyDictionary<>();
        /*symbolTable.keySet().forEach(p->symbolTable2.put(p,(IntValue) symbolTable.get(p)));
        MyIDictionary<String, Integer> symbolTable3 = new MyDictionary<>();
        symbolTable2.keySet().forEach(p->symbolTable3.put(p,symbolTable2.get(p).getVal()));*/

        List<Map.Entry<String, Value>> symbolTableList = new ArrayList<>();
        for(Map.Entry<String, Value> entry : symbolTable.getAll())
            symbolTableList.add(entry);
        symbolTableView.refresh();
        symbolTableView.setItems(FXCollections.observableList(symbolTableList));
        symbolTableView.refresh();
    }

    private void populateExecutionStack(ProgramState currentProgramState) {
        MyIStack<IStatement> executionStack = currentProgramState.getExecutionStack();

        List<String> executionStackList = new ArrayList<>();
        for(IStatement s : executionStack.getAll()){
            executionStackList.add(s.toString());
        }
        executionStackListView.refresh();
        executionStackListView.setItems(FXCollections.observableList(executionStackList));
        executionStackListView.refresh();
    }
}