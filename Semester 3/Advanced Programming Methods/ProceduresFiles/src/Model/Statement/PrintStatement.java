package Model.Statement;

import Collection.Heap.MyHeap;
import Collection.Heap.MyIHeap;
import Collection.Stack.MyIStack;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;
import Collection.Dictionary.MyIDictionary;
import Model.Value.Value;

public class PrintStatement implements IStatement{
    Expression expression;

    public PrintStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ToyLanguageInterpreterException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageInterpreterException {
        try{
            MyIHeap<Integer,Value> heap = state.getHeap();
            MyIStack<MyIDictionary<String,Value>> stackSymbolTable = state.getSymbolTable();
            MyIDictionary<String, Value> symbolTable = stackSymbolTable.peek();
            state.addOut(expression.evaluate(symbolTable,heap));
        }
        catch (ToyLanguageInterpreterException e){
            throw new ToyLanguageInterpreterException(e.getMessage());
        }
        return null;
    }
    public IStatement cloneStatement(){
        return new PrintStatement(expression);
    }
    @Override
    public String toString() {
        return "print( " + expression.toString() + " )";
    }
}