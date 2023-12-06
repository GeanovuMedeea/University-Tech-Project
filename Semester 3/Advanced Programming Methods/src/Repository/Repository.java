package Repository;

import Collection.List.MyIList;
import Collection.List.MyList;
import Model.Exceptions.FileException;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private MyIList<ProgramState> programStateList;
    private String logFilePath;
    /*private ProgramState programStateInRepo;

    private String logFilePath;

    public Repository() {
        logFilePath = "";
    }

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
    }

    public Repository(ProgramState programStateToSet, String logFilePath){
        this.programStateInRepo = programStateToSet;
        this.logFilePath = logFilePath;
    }
    public ProgramState getCurrentProgram(){
        return programStateInRepo;
    }
    public void setProgramState(ProgramState programStateToSet){
        this.programStateInRepo = programStateToSet;
    }

    public void setBackToOriginalProgramState(){
        this.programStateInRepo.setBackToOriginalState();
    }
    public void logPrgStateExec(ProgramState prgState) throws IOException{
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(prgState.toString());
        logFile.close();
    }*/

    public Repository() {
        logFilePath = "";
        programStateList = new MyList<>();
    }

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
        programStateList = new MyList<>();
    }

    public Repository(MyIList<ProgramState> programStateToSet){
        this.programStateList = programStateToSet;
    }

    public Repository(MyIList<ProgramState> programStateToSet, String logFilePath){
        this.programStateList = programStateToSet;
        this.logFilePath = logFilePath;
    }
    public void setFileLocation(String fileNameToSet){
        this.logFilePath = fileNameToSet;
    }
   @Override
    public void setProgramStateList(MyList<ProgramState> programStateList) {
        this.programStateList = programStateList;
    }

    @Override
    public MyIList<ProgramState> getProgramStateList() {
        return programStateList;
    }

    @Override
    public ProgramState getCurrentProgram() {
        return programStateList.get(programStateList.getListSize().getVal()-1);
    }

    public void setBackToOriginalProgramState(){
        for(int i = 0; i<programStateList.getListSize().getVal();i++)
            this.programStateList.get(i).setBackToOriginalState();
    }
    @Override
    public void logPrgStateExec(ProgramState prgState) throws FileException,IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(prgState.toString());
        logFile.close();
    }
}