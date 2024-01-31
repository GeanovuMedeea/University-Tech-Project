package GUI;

import Collection.ProcTable.MyProcTable;
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
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import Collection.ProcTable.MyIProcTable;
import Collection.Stack.MyStack;
import Collection.Dictionary.MyIDictionary;
import Collection.Stack.MyIStack;
import Collection.Dictionary.MyDictionary;
import Model.Value.Value;
public class SelectOptionController implements Initializable {
    private List<IStatement> programStatements;
    private RunProgramController mainWindowController;
    private MyIProcTable procTable;

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


        IStatement ex13 = buildExample(new DeclarationStatement("v", new IntType()), new AssignStatement("v", new ConstantExpression(new IntValue(2))),new PrintStatement(new VariableExpression("v")));

        IStatement ex14 = buildExample(
                new DeclarationStatement("v", new IntType()), new DeclarationStatement("w", new IntType()),
                new AssignStatement("v", new ConstantExpression(new IntValue(2))),
                new AssignStatement("w", new ConstantExpression(new IntValue(5))),
                new CallProcStatement("sum", new ArrayList<>(Arrays.asList(new ArithmeticExpression(new VariableExpression("v"), new ConstantExpression(new IntValue(10)),OperationSelection.MULTIPLY), new VariableExpression("w")))),
                new PrintStatement(new VariableExpression("v")),
                new ForkStatement(buildExample(
                new CallProcStatement("product", new ArrayList<>(Arrays.asList(new VariableExpression("v"), new VariableExpression("w")))),
                new ForkStatement(new CallProcStatement("sum", new ArrayList<>(Arrays.asList(new VariableExpression("v"), new VariableExpression("w"))))
                )))
        );

        programStatements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11, ex12,ex13,ex14));}

    private List<String> getStringRepresentations(){
        return programStatements.stream().map(IStatement::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.procTable = new MyProcTable();

        IStatement sumProc = buildExample(
                new DeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"),OperationSelection.MULTIPLY)),
                new PrintStatement(new VariableExpression("v")));

        List<String> varSum = new ArrayList<>();
        varSum.add("a");
        varSum.add("b");
        procTable.put("sum", new Pair<>(varSum, sumProc));

        IStatement prodProc = buildExample(
                new DeclarationStatement("v" , new IntType()),
                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"),OperationSelection.MULTIPLY)),
                new PrintStatement(new VariableExpression("v")));

        List<String> varProd = new ArrayList<>();
        varProd.add("a");
        varProd.add("b");
        procTable.put("product", new Pair<>(varProd, prodProc));


        buildProgramStatements();
        programListView.setItems(FXCollections.observableArrayList(getStringRepresentations()));

        executeButton.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();

            if(index >= 0) {
                ProgramState initialProgramState = new ProgramState(programStatements.get(index));
                initialProgramState.setProcTable(procTable);
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