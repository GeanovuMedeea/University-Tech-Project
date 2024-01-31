package Model;

import Collection.Dictionary.MyDictionary;
import Collection.Dictionary.MyIDictionary;
import Collection.Heap.MyHeap;
import Collection.Heap.MyIHeap;
import Collection.List.MyIList;
import Collection.List.MyList;
import Collection.ProcTable.MyIProcTable;
import Collection.ProcTable.MyProcTable;
import Collection.Stack.MyIStack;
import Collection.Stack.MyStack;
import Model.Exceptions.ADTEmptyException;
import Model.Exceptions.ToyLanguageInterpreterException;
//import Model.Statement.File.MyFilePair;
//import Model.Statement.Collection.Heap.HeapAddressBuilder;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.chrono.IsoChronology;
import java.util.*;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    //private MyIDictionary<String, Value> symbolTable;
    private MyIHeap<Integer, Value> heap;
    private MyIList<Value> outputList;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    private MyIStack<MyIDictionary<String, Value>> symbolTable;

    private MyIProcTable procTable;


    private IStatement originalProgram;

    private static final Set<Integer> ids = new TreeSet<>();
    public Integer id;

    public ProgramState(MyStack<IStatement> programStateMyStack, MyIStack<MyIDictionary<String,Value>>  symbolTable, MyIHeap<Integer,Value> heap,MyIProcTable procTable, MyIList<Value> outputList, IStatement originalProgram, MyIDictionary<StringValue,BufferedReader> fileTable) {
        executionStack = programStateMyStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.outputList = outputList;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.procTable = procTable;
        this.id = newId();

        executionStack.push(originalProgram);
    }

    public ProgramState(Boolean flag, MyStack<IStatement> programStateMyStack, MyIStack<MyIDictionary<String,Value>>  symbolTable, MyIHeap<Integer,Value> heap, MyIProcTable procTable,MyIList<Value> outputList, IStatement originalProgram, MyIDictionary<StringValue,BufferedReader> fileTable) {
        executionStack = programStateMyStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.outputList = outputList;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.procTable = procTable;
        this.id = newId();
    }

    public ProgramState(IStatement originalProgram){
        executionStack = new MyStack<>();
        symbolTable = new MyStack<>();
        heap = new MyHeap<>();
        outputList = new MyList<>();
        fileTable = new MyDictionary<>();
        this.originalProgram = originalProgram.cloneStatement();
        this.procTable = new MyProcTable();
        this.id = newId();

        executionStack.push(originalProgram);
    }

    public void setState(MyStack<IStatement> programStateMyStack, MyIStack<MyIDictionary<String,Value>>  symbolTable, MyIHeap<Integer,Value> heap,MyIProcTable procTable, MyIList<Value> outputList, IStatement originalProgram, MyIDictionary<StringValue,BufferedReader> fileTable)
    {   executionStack = programStateMyStack;
        this.symbolTable = symbolTable;
        this.heap = heap;
        this.outputList = outputList;
        this.originalProgram = originalProgram;
        this.fileTable = fileTable;
        this.procTable = procTable;
        this.id = newId();
    }

    private static Integer newId() {
        Random rand = new Random();
        Integer id = 1;
        //Integer id = rand.nextInt(100);
        synchronized (ids){
            while(id <= 0 || ids.contains(id))
                id=rand.nextInt(100);
            ids.add(id);
        }
        return id;
    }
    public void setSymbolTable(MyIStack<MyIDictionary<String,Value>> newSymTable) {
        this.symbolTable = newSymTable;
    }
    public String symTableToString() throws ToyLanguageInterpreterException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        List<MyIDictionary<String, Value>> stack = symbolTable.getAll();
        for (MyIDictionary<String, Value> table: stack) {
            for (String key: table.keySet()) {
                symTableStringBuilder.append(String.format("%s -> %s\n", key, table.get(key).toString()));
            }
            symTableStringBuilder.append("\n");
        }
        return symTableStringBuilder.toString();
    }


    public Integer getId(){return this.id;}

    public MyIStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public MyIStack<MyIDictionary<String,Value>>  getSymbolTable() {
        return symbolTable;
    }
    public MyIHeap<Integer, Value> getHeap() {return heap;}
    public void setProcTable(MyIProcTable procTable) {
        this.procTable = procTable;
    }
    public MyIProcTable getProcTable() {
        return procTable;
    }

    public MyIList<Value> getOutputList() {
        return outputList;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() { return fileTable;}

    public void addOut(Value number) {
        this.outputList.add(number);
    }

    public Boolean isNotCompleted() {
        return !executionStack.isEmpty().getVal();
    }

    public ProgramState oneStep() throws IOException, ToyLanguageInterpreterException {
    //MyIStack<IStatement> stack = state.getExecutionStack();
    if(executionStack.isEmpty().getVal())
        throw new ADTEmptyException("ProgramState stack is empty");
    IStatement currentStatement = executionStack.pop();
    //System.out.println("inainte\n");
    //.out.println(this);
    //System.out.println("dupa\n");
    //System.out.println(this);
    return currentStatement.execute(this);
    }
    public MyIDictionary<String, Value> getTopSymTable() {
        try {
            return symbolTable.peek();
        } catch (ToyLanguageInterpreterException e) {
            System.out.println("Stack is empty!");
            symbolTable.push(new MyDictionary<>());
            return symbolTable.getAll().get(0);
        }
    }
    public void setBackToOriginalState(){
        if(this.executionStack.isEmpty().getVal()){
            this.executionStack.push(originalProgram);
            this.symbolTable = new MyStack<>();
            this.heap = new MyHeap<>();
            this.outputList = new MyList<>();
            this.fileTable = new MyDictionary<>();
        }
    }

    @Override
    public String toString() {
        try {
            return "------------------------------------------------------\n" +
                    "Thread number id: " + id.toString() + "\n" +
                    "-----ExecutionStack-----\n" +
                    executionStack.toString() + "\n" +
                    "-----OutputList-----\n" +
                    outputList.toString() + "\n" +
                    "-----SymbolTable-----\n" +
                    symTableToString() + "\n" +
                    "--------Heap--------\n"+
                    heap.toString() + "\n" +
                    "------FileTable------" + "\n"+
                    fileTable.toString() + "\n"+
                    "-----ProcTable----"+
                    procTable.toString()+"\n"+
                    "------------------------------------------------------\n\n\n";
        } catch (ToyLanguageInterpreterException e) {
            throw new RuntimeException(e);
        }
    }
}