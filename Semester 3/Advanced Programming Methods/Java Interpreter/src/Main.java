import Collection.Dictionary.MyDictionary;
import Collection.Dictionary.MyIDictionary;
import Collection.List.MyIList;
import Collection.List.MyList;
import Collection.Stack.MyIStack;
import Collection.Stack.MyStack;
import Controller.Controller;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import View.ExitCommand;
import View.RunCommand;
import View.TextMenu;


public class Main {
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

    public static void main(String[] args) {
        /*IStatement ex1 = buildExample(new DeclarationStatement("v",new IntType()),
                new AssignStatement("v", new ConstantExpression(new IntValue(2))),
                new PrintStatement(new VariableExpression("v"))
        );
        ProgramState prg1 = new ProgramState(ex1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctrl1 = new Controller(repo1);

        IStatement ex2 = buildExample(new DeclarationStatement("a",new IntType()), new DeclarationStatement("b",new IntType()),
                new AssignStatement("a", new ArithmeticExpression(new ConstantExpression(new IntValue(2)), new
                ArithmeticExpression(new ConstantExpression(new IntValue(3)), new ConstantExpression(new IntValue(5)),
                        OperationSelection.MULTIPLY), OperationSelection.PLUS)),
                new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new
                        ConstantExpression(new IntValue(1)), OperationSelection.PLUS)),
                new PrintStatement(new VariableExpression("b")));
        ProgramState prg2 = new ProgramState(ex2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);

        IStatement ex3 = buildExample(new DeclarationStatement("a", new BoolType()),
                new AssignStatement("a", new ConstantExpression(new BoolValue(false))),
                new DeclarationStatement("v", new IntType()),
                new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ConstantExpression(new IntValue(2))),
                        new AssignStatement("v", new ConstantExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))
        );
        ProgramState prg3 = new ProgramState(ex3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);*/

        IStatement ex1 = buildExample(new DeclarationStatement("v",new IntType()),
                new AssignStatement("v", new ConstantExpression(new IntValue(2))),
                new PrintStatement(new VariableExpression("v"))
        );
        MyIList<ProgramState> prg1 = new MyList<>();
        prg1.add(new ProgramState(ex1));
        IRepository repo1 = new Repository(prg1);
        Controller ctrl1 = new Controller(repo1);

        IStatement ex2 = buildExample(new DeclarationStatement("a",new IntType()), new DeclarationStatement("b",new IntType()),
                new AssignStatement("a", new ArithmeticExpression(new ConstantExpression(new IntValue(2)), new
                        ArithmeticExpression(new ConstantExpression(new IntValue(3)), new ConstantExpression(new IntValue(5)),
                        OperationSelection.MULTIPLY), OperationSelection.PLUS)),
                new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new
                        ConstantExpression(new IntValue(1)), OperationSelection.PLUS)),
                new PrintStatement(new VariableExpression("b")));
        MyIList<ProgramState> prg2 = new MyList<>();
        prg2.add(new ProgramState(ex2));
        IRepository repo2 = new Repository(prg2);
        Controller ctrl2 = new Controller(repo2);

        IStatement ex3 = buildExample(new DeclarationStatement("a", new BoolType()),
                new AssignStatement("a", new ConstantExpression(new BoolValue(false))),
                new DeclarationStatement("v", new IntType()),
                new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ConstantExpression(new IntValue(2))),
                        new AssignStatement("v", new ConstantExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))
        );
        MyIList<ProgramState> prg3 = new MyList<>();
        prg3.add(new ProgramState(ex3));
        IRepository repo3 = new Repository(prg3);
        Controller ctrl3 = new Controller(repo3);

        /*IStatement ex4 = buildExample(new DeclarationStatement("a",new BoolType()),
                new AssignStatement("a", new BooleanExpression(new ConstantExpression(new IntValue(2)), new
                        ConstantExpression(new IntValue(13)), "<")),
                new PrintStatement(new VariableExpression("a")));*/
        IStatement ex4 = buildExample(new DeclarationStatement("a",new BoolType()),
        new AssignStatement("a", new LogicExpression(new BooleanExpression(new ConstantExpression(new IntValue(2)), new
                ConstantExpression(new IntValue(13)), "<"), new BooleanExpression(new ConstantExpression(new IntValue(10)),
                new ConstantExpression(new IntValue(4)), ">="), BinaryOperationSelection.AND)),
        new PrintStatement(new VariableExpression("a")));
        MyIList<ProgramState> prg4 = new MyList<>();
        prg4.add(new ProgramState(ex4));
        IRepository repo4 = new Repository(prg4);
        Controller ctrl4 = new Controller(repo4);

        IStatement ex5 = buildExample();
        MyIList<ProgramState> prg5 = new MyList<>();
        prg5.add(new ProgramState(ex5));
        IRepository repo5 = new Repository(prg5);
        Controller ctrl5 = new Controller(repo5);

        /*IStatement ex6 = buildExample(new DeclarationStatement("a",new BoolType()),
                new AssignStatement("a", new LogicExpression(new BooleanExpression(new ConstantExpression(new IntValue(2)), new
                        ConstantExpression(new IntValue(13)), "<"), new BooleanExpression(new ConstantExpression(new IntValue(10)),
                        new ConstantExpression(new IntValue(4)), ">="), BinaryOperationSelection.AND)),
                new PrintStatement(new VariableExpression("a")));
        MyStack<IStatement> stk = new MyStack<>();
        MyDictionary<String, Value> symTable = new MyDictionary<>();
        MyList<Value> out = new MyList<>();
        ProgramState crtPrgState = new ProgramState(stk,symTable,out,ex6);
        IRepository repo6 = new Repository(crtPrgState);
        repo6.setProgramStateList();

        MyIList<ProgramState> prg6 = new MyList<>();
        prg6.add(new ProgramState(ex6));
        IRepository repo6 = new Repository(prg6);
        Controller ctrl6 = new Controller(repo6);*/

        IStatement ex6 = buildExample( new DeclarationStatement("varf", new StringType()),
                new AssignStatement("varf", new ConstantExpression(new StringValue("test.in"))),
                new OpenRFileStatement(new VariableExpression("varf")),
                new DeclarationStatement("vare", new IntType()),
                new ReadFileStatement(new VariableExpression("varf"), "vare"),
                new PrintStatement(new VariableExpression("vare")),
                new ReadFileStatement(new VariableExpression("varf"), "vare"),
                new PrintStatement(new VariableExpression("vare")),
                new CloseRFileStatement(new VariableExpression("varf")));
        MyIList<ProgramState> prg6 = new MyList<>();
        prg6.add(new ProgramState(ex6));
        IRepository repo6 = new Repository(prg6);
        Controller ctrl6 = new Controller(repo6);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunCommand("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunCommand("5", ex5.toString(), ctrl5));
        menu.addCommand(new RunCommand("6", ex6.toString(), ctrl6));


        menu.show();
    }
}