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
import Model.Type.RefType;
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

        IStatement ex7 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new DeclarationStatement("a",new RefType(new RefType(new IntType()))),
                new NewStatement("a",new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new VariableExpression("a"))
        );
        MyIList<ProgramState> prg7 = new MyList<>();
        prg7.add(new ProgramState(ex7));
        IRepository repo7 = new Repository(prg7);
        Controller ctrl7 = new Controller(repo7);

        IStatement ex8 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new DeclarationStatement("a",new RefType(new RefType(new IntType()))),
                new NewStatement("a",new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("a")),new ConstantExpression(new IntValue(5)),OperationSelection.PLUS))
        );
        MyIList<ProgramState> prg8 = new MyList<>();
        prg8.add(new ProgramState(ex8));
        IRepository repo8 = new Repository(prg8);
        Controller ctrl8 = new Controller(repo8);

        IStatement ex9 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new WriteHeapStatement("v",new ConstantExpression(new IntValue(30))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")),new ConstantExpression(new IntValue(5)),OperationSelection.PLUS))
        );
        MyIList<ProgramState> prg9 = new MyList<>();
        prg9.add(new ProgramState(ex9));
        IRepository repo9 = new Repository(prg9);
        Controller ctrl9 = new Controller(repo9);

        IStatement ex10 = buildExample(new DeclarationStatement("v", new RefType(new IntType())),
                new NewStatement("v",new ConstantExpression(new IntValue(20))),
                new DeclarationStatement("a",new RefType(new RefType(new IntType()))),
                new NewStatement("a",new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
        MyIList<ProgramState> prg10 = new MyList<>();
        prg10.add(new ProgramState(ex10));
        IRepository repo10 = new Repository(prg10);
        Controller ctrl10 = new Controller(repo10);

        IStatement ex11 = buildExample(new DeclarationStatement("v", new IntType()),
                new AssignStatement("v", new ConstantExpression(new IntValue(4))),
                new WhileStatement(new BooleanExpression(new VariableExpression("v"),new ConstantExpression(new IntValue(0)),">"),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(new IntValue(1)),OperationSelection.MINUS)))),
                new PrintStatement(new VariableExpression("v"))
        );
        MyIList<ProgramState> prg11 = new MyList<>();
        prg11.add(new ProgramState(ex11));
        IRepository repo11 = new Repository(prg11);
        Controller ctrl11 = new Controller(repo11);

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
        MyIList<ProgramState> prg12 = new MyList<>();
        prg12.add(new ProgramState(ex12));
        IRepository repo12 = new Repository(prg12);
        Controller ctrl12 = new Controller(repo12);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunCommand("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunCommand("5", ex5.toString(), ctrl5));
        menu.addCommand(new RunCommand("6", ex6.toString(), ctrl6));
        menu.addCommand(new RunCommand("7",ex7.toString(), ctrl7));
        menu.addCommand(new RunCommand("8",ex8.toString(), ctrl8));
        menu.addCommand(new RunCommand("9",ex9.toString(), ctrl9));
        menu.addCommand(new RunCommand("10",ex10.toString(), ctrl10));
        menu.addCommand(new RunCommand("11",ex11.toString(), ctrl11));
        menu.addCommand(new RunCommand("12",ex12.toString(), ctrl12));

        menu.show();
    }
}