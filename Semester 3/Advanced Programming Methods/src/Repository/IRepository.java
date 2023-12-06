package Repository;

import Collection.List.MyIList;
import Collection.List.MyList;
import Model.Exceptions.FileException;
import Model.ProgramState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IRepository {
    ProgramState getCurrentProgram();
    MyIList<ProgramState> getProgramStateList();
    void setProgramStateList(MyList<ProgramState> programStateList);
    void logPrgStateExec(ProgramState prgState) throws FileException, IOException;
    void setBackToOriginalProgramState();
    void setFileLocation(String fileNameToSet);

    /*ProgramState getCurrentProgram();
    //ProgramState getProgramState();
    void setProgramState(ProgramState programStateToSet);
    void setBackToOriginalProgramState();*/
}