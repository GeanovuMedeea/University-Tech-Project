import java.util.Scanner;

public class FAMain {

    static void printMenu(){
        System.out.println("\n1. Display states");
        System.out.println("2. Display alphabet");
        System.out.println("3. Display transitions");
        System.out.println("4. Display initial state");
        System.out.println("5. Display final states");
        System.out.println("6. Check if DFA");
        System.out.println("7. Check if sequence is accepted (DFA only)");
        System.out.println("8. Exit");
        System.out.print(">> ");
    }

    public static void main(String[] args) {
        FA fa = new FA();
        try {
            fa.loadFAFromFile("FA.in");
            System.out.println("Finite Automaton loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading FA: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> System.out.println("States: " + fa.getStates());
                case 2 -> System.out.println("Alphabet: " + fa.getAlphabet());
                case 3 -> System.out.println("Transitions: " + fa.getTransitions());
                case 4 -> System.out.println("Initial State: " + fa.getInitialState());
                case 5 -> System.out.println("Final States: " + fa.getFinalStates());
                case 6 -> System.out.println("Is DFA: " + fa.isDFA());
                case 7 -> {
                    if (!fa.isDFA()) {
                        System.out.println("Not a DFA, cannot check sequence acceptance.");
                    } else {
                        System.out.print("Enter sequence to check: ");
                        //scanner.nextLine();
                        String sequence = scanner.nextLine();
                        System.out.println("Sequence accepted: " + fa.acceptsSequence(sequence));
                    }
                }
                case 8 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
