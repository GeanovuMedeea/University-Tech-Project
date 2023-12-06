package View;

import Controller.Controller;
import Model.Exceptions.FileException;
import Model.Exceptions.ToyLanguageInterpreterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RunCommand extends Command{
    private Controller controller;

    public RunCommand(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute(boolean flag, String fileName) {
        try{
            controller.allSteps(flag, fileName);
        } catch (InterruptedException | ToyLanguageInterpreterException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}