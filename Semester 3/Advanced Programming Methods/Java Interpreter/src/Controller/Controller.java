package Controller;

import Collection.Dictionary.MyDictionary;
import Collection.Dictionary.MyIDictionary;
import Collection.List.MyIList;
import Collection.List.MyList;
import Collection.Stack.MyIStack;
import Model.Exceptions.ADTEmptyException;
import Model.Exceptions.ComparisonException;
import Model.Exceptions.FileException;
import Model.Exceptions.ToyLanguageInterpreterException;
import Model.Expression.Expression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
//import Model.Statement.File.CloseStatement;
import Model.Statement.IStatement;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Period;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;

    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    Map<Integer, Value> garbageCollector(Set<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Set<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Map<Integer, Value> heap) {
        Set<Integer> toReturn = new TreeSet<>();
        symTableValues.stream().filter(v -> v instanceof RefValue)
                .forEach(v -> {
                    while (v instanceof RefValue) {
                        toReturn.add(((RefValue) v).getVal());
                        v = heap.get(((RefValue) v).getVal());
                    }
                });

        return toReturn;
    }

    MyIList<ProgramState> removeCompletedPrg(MyIList<ProgramState> inPrgList) {
        List<ProgramState> temp = inPrgList.stream()
                .filter(ProgramState::isNotCompleted).collect(Collectors.toList());
        MyIList<ProgramState> listToReturn = new MyList<>();
        temp.forEach(listToReturn::add);
        return listToReturn;
    }

    /*private ProgramState oneStep(ProgramState state) throws IOException, InterruptedException, FileException, ToyLanguageInterpreterException, ADTEmptyException {
        MyIStack<IStatement> stack = state.getExecutionStack();
        if(stack.isEmpty().getVal())
            throw new ADTEmptyException("ProgramState stack is empty");
        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }*/

    /*public void allSteps(boolean flag, String fileName) throws IOException, FileException, InterruptedException, ToyLanguageInterpreterException {
        this.repository.setFileLocation(fileName);
        ProgramState program = repository.getCurrentProgram();
        repository.logPrgStateExec(program);
        if(!flag)
            printThings();
        while(!program.getExecutionStack().isEmpty().getVal()){
            oneStep(program);
            repository.logPrgStateExec(program);
            program.getHeap().setContent(
                    garbageCollector(getAddrFromSymTable(program.getSymbolTable().getValues(),program.getHeap().getContent()), program.getHeap().getContent()));
            if(!flag)
                printThings();
        }
        if(flag)
            printThings();
        repository.setBackToOriginalProgramState();
    }*/

    public void runTypeChecker() throws ToyLanguageInterpreterException {
        for (ProgramState state: repository.getProgramStateList().stream().toList()) {
            MyIDictionary<String, Type> typeTable = new MyDictionary<>();
            state.getExecutionStack().peek().typeCheck(typeTable);
        }
    }

    public void oneStepForAllPrg(MyIList<ProgramState> prgList) throws IOException, ToyLanguageInterpreterException, InterruptedException {
            prgList.forEach(prg -> {
                try {
                    repository.logPrgStateExec(prg);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.exit(1);}
            });

            List<Callable<ProgramState>> callList = prgList.stream()
                    .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                    .toList();

            List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                            .map(future->{try {return future.get();}
                            catch(InterruptedException |ExecutionException e){
                            System.out.println(e.getMessage());
                            System.exit(1);
                            return null;}
                            }).filter(p->p!=null).collect(Collectors.toList());

            prgList.addAll(newPrgList);
            prgList.forEach(prg -> {
                try {
                    repository.logPrgStateExec(prg);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            });
            repository.setProgramStateList(prgList);
    }

    public void allSteps(boolean flag, String fileName) throws IOException,InterruptedException,ToyLanguageInterpreterException
    {
        this.repository.setFileLocation(fileName);
        this.runTypeChecker();
        executor = Executors.newFixedThreadPool(2);
        MyIList<ProgramState> prgList = removeCompletedPrg(repository.getProgramStateList());
        while(prgList.getListSize().getVal()>0) {
            ProgramState program = prgList.get(0);
            program.getHeap().setContent(
                    garbageCollector(getAddrFromSymTable(program.getSymbolTable().getValues(),program.getHeap().getContent()), program.getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repository.getProgramStateList());
        }
        executor.shutdownNow();
        repository.setProgramStateList(prgList);
        //repository.setBackToOriginalProgramState();
    }
}

    /*public void runTypeChecker() throws ToyLanguageInterpreterException {
        for(int i = 0; i <= repository.getProgramStateList().getListSize().getVal(); i++)
        {
            ProgramState state = repository.getProgramStateList().get(i);
            MyIDictionary<String, Type> typeTable = new MyDictionary<>();
            state.getExecutionStack().peek().typeCheck(typeTable);
        }
        /*for (ProgramState state: repository.getProgramStateList()) {
            //System.out.println(state.getExecutionStack().peek());
            MyIDictionary<String, Type> typeTable = new MyDictionary<>();
            state.getExecutionStack().peek().typeCheck(typeTable);
        }
        }
     */
    /*private void oneStepForAllProgram(MyList<ProgramState> states) throws InterruptedException {
        states.forEach(prg -> {
            try {
                //printThings();
                repository.logPrgStateExec(prg);
                try{
                runTypeChecker();}
                catch(Exception e){}
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = states.stream().filter(p->!p.getExecutionStack().isEmpty().getVal())
                .map((ProgramState p) ->
                        (Callable<ProgramState>)(()->{
                            try{
                                return p.oneStep();}
                            catch (ToyLanguageInterpreterException e){
                                System.out.println(e.getMessage());
                                return null;
                            }
                        })
                ).collect(Collectors.toList());
        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println("End of program");
                    }
                    return null;
                }).filter(p->(p!=null)).collect(Collectors.toList());
        states.addAll(newProgramList);
        //printThings(); or you have the log files
        repository.setProgramStateList(states);
    }

    @SuppressWarnings("unchecked")
    public void allSteps() throws InterruptedException, ToyLanguageInterpreterException, IOException {
        runTypeChecker();
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> states = removeCompletedProgram(repository.getProgramStateList());
        // List<ProgramState> prgList = null;
        while(states.size() > 0){
            try {
                runTypeChecker();
            }
            catch(Exception e){
            }
            oneStepForAllProgram(states);
            states = removeCompletedProgram(repository.getProgramStateList());
        }
        //but we use it only for the final result, not for each level!!! if you want it to print all the steps, uncomment
        //the print in the one step method above
        printThings();
        repository.logPrgStateExec(repository.getCurrentProgram());

        executor.shutdownNow();
        repository.setProgramStateList(states);

    }
    */

    /*private void printThings(){
        ProgramState programState = repository.getCurrentProgram();
        System.out.print("------------------------------------------------------\n");
        System.out.println("Thread number id: " + id_thread.toString() + "\n") +
        System.out.print("-----ExecutionStack-----\n");
        System.out.print(programState.getExecutionStack().toString() + "\n");
        System.out.print("-----OutputList-----\n");
        System.out.print(programState.getOutputList().toString() + "\n");
        System.out.print("-----SymbolTable-----\n");
        System.out.print(programState.getSymbolTable().toString() + "\n");
        System.out.print("--------Heap--------\n");
        System.out.print(programState.getHeap().toString() + "\n");
        System.out.print("------FileTable------\n");
        System.out.print(programState.getFileTable().toString()+"\n");
        System.out.print("------------------------------------------------------\n");
    }*/