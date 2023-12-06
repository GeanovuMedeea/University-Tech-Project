package View;

public class ExitCommand extends Command {
    public ExitCommand(String key, String description){
        super(key, description);
    }

    @Override
    public void execute(boolean flag, String fileName) {
        System.exit(0);
    }
}