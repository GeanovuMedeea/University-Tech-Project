package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(){
        commands = new HashMap<>();
    }

    public void addCommand(Command c){
        commands.put(c.getKey(), c);
    }

    private void printMenu(){
        for(Command com : commands.values()){
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue; }
            boolean flag = false;
            String flagString, fileName="";
            if (!key.equals("0")) {
                System.out.printf("Final result or step by step? (final = true) (step by step = false): ");
                flagString=scanner.nextLine();
                flag = Boolean.valueOf(flagString);
                System.out.printf("File log location: ");
                fileName=scanner.nextLine();
            }
            com.execute(flag, fileName);
        }
    }
}