package GUI;

import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.*;
import Controller.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Statement.CloseRFileStatement;
import Model.Statement.OpenRFileStatement;
import Model.Statement.ReadFileStatement;
import Model.Statement.NewStatement;
import Model.Statement.WriteHeapStatement;
import Repository.IRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectOptionController implements Initializable {
    private List<IStatement> programStatements;
    private RunProgramController mainWindowController;

    @FXML
    private ListView<String> programListView;

    @FXML
    private Button executeButton;

    public void setMainWindowController(RunProgramController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    static IStatement buildExample(IStatement... statements){
        if(statements.length == 0)
            return new NopStatement();
        if(statements.length == 1)
            return statements[0];
        IStatement finalStatement = new CompoundStatement(statements[0], statements[1]);
        for (int i = 2; i < statements.length; ++i)
            finalStatement = new CompoundStatement(finalStatement, statements[i]);
        return finalStatement;
    }
    private void buildProgramStatements()
    {
        IStatement ex1 = buildExample(new DeclarationStatement("v",new IntType()),
                new AssignStatement("v", new ConstantExpression(new IntValue(2))),
                new PrintStatement(new VariableExpression("v"))
        );


        IStatement ex2 = buildExample(new DeclarationStatement("a",new IntType()), new DeclarationStatement("b",new IntType()),
                new AssignStatement("a", new ArithmeticExpression(new ConstantExpression(new IntValue(2)), new
                        ArithmeticExpression(new ConstantExpression(new IntValue(3)), new ConstantExpression(new IntValue(5)),
                        OperationSelection.MULTIPLY), OperationSelection.PLUS)),
                new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new
                        ConstantExpression(new IntValue(1)), OperationSelection.PLUS)),
                new PrintStatement(new VariableExpression("b")));


        IStatement ex3 = buildExample(new DeclarationStatement("a", new BoolType()),
                new AssignStatement("a", new ConstantExpression(new BoolValue(false))),
                new DeclarationStatement("v", new IntType()),
                new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ConstantExpression(new IntValue(2))),
                        new AssignStatement("v", new ConstantExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))
        );

        IStatement ex4 = buildExample(new DeclarationStatement("a",new BoolType()),
                new AssignStatement("a", new LogicExpression(new BooleanExpression(new ConstantExpression(new IntValue(2)), new
                        ConstantExpression(new IntValue(13)), "<"), new BooleanExpression(new ConstantExpression(new IntValue(10)),
                        new ConstantExpression(new IntValue(4)), ">="), BinaryOperationSelection.AND)),
                new PrintStatement(new VariableExpression("a")));


        IStatement ex5 = buildExample();


        IStatement ex6 = buildExample( new DeclarationStatement("varf", new StringType()),
                new AssignStatement("varf", new ConstantExpression(new StringValue("test.in"))),
                new OpenRFileStatement(new VariableExpression("varf")),
                new DeclarationStatement("vare", new IntType()),
                new ReadFileStatement(new VariableExpression("varf"), "vare"),
                new PrintStatement(new VariableExpression("vare")),
                new ReadFileStatement(new VariableExpression("varf"), "vare"),
                new PrintStatement(new VariableExpression("vare")),
                new CloseRFileStatement(new VariableExpression("varf")));

        IStatement ex7 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new DeclarationStatement("a",new RefType(new RefType(new IntType()))),
                new NewStatement("a",new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new VariableExpression("a"))
        );

        IStatement ex8 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new DeclarationStatement("a",new RefType(new RefType(new IntType()))),
                new NewStatement("a",new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("a")),new ConstantExpression(new IntValue(5)),OperationSelection.PLUS))
        );


        IStatement ex9 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new WriteHeapStatement("v",new ConstantExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")),new ConstantExpression(new IntValue(5)),OperationSelection.PLUS))
        );


        IStatement ex10 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new DeclarationStatement("a",new RefType(new RefType(new IntType()))),
                new NewStatement("a",new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );


        IStatement ex11 = buildExample(new DeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ConstantExpression(new IntValue(4))),
                new WhileStatement(new BooleanExpression(new VariableExpression("v"),new ConstantExpression(new IntValue(0)),">"),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(new IntValue(1)),OperationSelection.MINUS)))),
                new PrintStatement(new VariableExpression("v"))
        );


        IStatement ex12 = buildExample(new DeclarationStatement("v", new IntType()),
                new DeclarationStatement("a", new RefType(new IntType())),
                new AssignStatement("v", new ConstantExpression(new IntValue(10))),
                new NewStatement("a", new ConstantExpression(new IntValue(22))),
                new ForkStatement(buildExample(new WriteHeapStatement("a", new ConstantExpression(new IntValue(30))),
                        new AssignStatement("v", new ConstantExpression(new IntValue(32))),
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );

        IStatement ex13 = buildExample(new DeclarationStatement("v1",new RefType(new IntType())), new DeclarationStatement("cnt",new IntType()),
                new NewStatement("v1",new ConstantExpression(new IntValue(1))), new CreateSemaphoreStatement("cnt",new ReadHeapExpression(new VariableExpression("v1"))),
                new ForkStatement(buildExample(new AcquireStatement("cnt"),
                        new WriteHeapStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),
                                new ConstantExpression(new IntValue(10)),OperationSelection.MULTIPLY)),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseStatement("cnt"))),
                new ForkStatement(buildExample(new AcquireStatement("cnt"),
                        new WriteHeapStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),
                                new ConstantExpression(new IntValue(10)),OperationSelection.MULTIPLY)),
                        new WriteHeapStatement("v1",new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),
                                                new ConstantExpression(new IntValue(2)),OperationSelection.MULTIPLY)),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseStatement("cnt"))),
                new AcquireStatement("cnt"),new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")),new ConstantExpression(new IntValue(1)),OperationSelection.MINUS)),
                new ReleaseStatement("cnt")
                );

        programStatements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12,ex13));}

    private List<String> getStringRepresentations(){
        return programStatements.stream().map(IStatement::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        buildProgramStatements();
        programListView.setItems(FXCollections.observableArrayList(getStringRepresentations()));

        executeButton.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();

            if(index >= 0) {
                ProgramState initialProgramState = new ProgramState(programStatements.get(index));
                IRepository repository = new Repository("log" + (index + 1) + ".txt");
                repository.addProgramState(initialProgramState);
                Controller ctrl = new Controller(repository);
                try {
                    ctrl.runTypeChecker();
                } catch (ToyLanguageInterpreterException e) {
                    throw new RuntimeException(e);
                }
                mainWindowController.setController(ctrl);
            }
            else return;
        });
    }
}