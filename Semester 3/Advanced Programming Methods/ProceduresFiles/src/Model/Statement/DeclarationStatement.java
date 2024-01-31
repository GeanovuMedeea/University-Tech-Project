package Model.Statement;
import Collection.Stack.MyIStack;
import Model.Exceptions.StatementExecutionException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.ProgramState;
import Collection.Dictionary.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class DeclarationStatement implements IStatement {
    private final String name;
    private final Type type;

    public DeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        typeEnv.put(name,type);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        MyIStack<MyIDictionary<String,Value>> stackSymbolTable = state.getSymbolTable();
        MyIDictionary<String, Value> symbolTable = stackSymbolTable.peek();
        if (symbolTable.containsKey(name))
            throw new StatementExecutionException(name + " already exists in the symTable");
        symbolTable.put(name, type.getDefaultValue());
        state.setSymbolTable(stackSymbolTable);
        return null;
    }
    public IStatement cloneStatement(){
        return new DeclarationStatement(name,type);
    }
    @Override
    public String toString() {
        return "Declaration{ " + name + " " + type.toString() + " }";
    }
}