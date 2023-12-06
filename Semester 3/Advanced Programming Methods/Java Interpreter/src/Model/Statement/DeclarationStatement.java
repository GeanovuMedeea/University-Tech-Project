package Model.Statement;
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
    public ProgramState execute(ProgramState state) throws StatementExecutionException {
        MyIDictionary<String, Value> symTable = state.getSymbolTable();
        if (symTable.containsKey(name))
            throw new StatementExecutionException(name + " already exists in the symTable");
        symTable.put(name, type.getDefaultValue());
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