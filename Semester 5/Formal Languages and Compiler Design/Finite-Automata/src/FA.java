import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class FA {
    private final Set<String> states;
    private final Set<String> alphabet;
    private final Map<Map<String, String>,String> transitions;
    private String initialState;
    private final Set<String> finalStates;

    public FA() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        transitions = new HashMap<>();
        finalStates = new HashSet<>();
    }

    public void loadFAFromFile(String filename) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();

        while (line != null) {
            line = line.trim();
            if (line.startsWith("[states]")) {
                line = reader.readLine();
                String[] stateTokens = line.split(",");
                Collections.addAll(states, stateTokens);
            } else if (line.startsWith("[alphabet]")) {
                line = reader.readLine();
                String[] alphabetTokens = line.split(",");
                Collections.addAll(alphabet, alphabetTokens);
            } else if (line.startsWith("[transitions]")) {
                line = reader.readLine();
                while (!line.equals("end")) {
                    String[] parts = line.split(",");
                    String fromState = parts[0].trim();
                    String symbol = parts[1].trim();
                    String toState = parts[2].trim();
                    switch (symbol) {
                        case "[0-9]" -> {
                            for(int i=0;i<=9;i++)
                                addTransition(fromState, String.valueOf(i), toState);
                        }
                        case "[1-9]" -> {
                            for(int i=1;i<=9;i++)
                                addTransition(fromState, String.valueOf(i), toState);
                        }
                        case "[A-Za-z]" -> {
                            for(int i=65;i<=90;i++)
                                addTransition(fromState, String.valueOf((char)i), toState);
                            for(int i=97;i<=122;i++)
                                addTransition(fromState, String.valueOf((char)i), toState);
                        }
                        default -> addTransition(fromState, symbol, toState);

                    }

                    line = reader.readLine();
                }
            } else if (line.startsWith("[initialState]")) {
                line = reader.readLine();
                initialState = line.trim();
            } else if (line.startsWith("[finalStates]")) {
                line = reader.readLine();
                String[] finalStateTokens = line.split(",");
                Collections.addAll(finalStates, finalStateTokens);
            }
            line = reader.readLine();
        }
        reader.close();
    }

    private void addTransition(String fromState, String symbol, String toState) {
        Map<String, String> key = new HashMap<>();
        key.put(fromState, symbol);
        transitions.put(key, toState);
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Map<Map<String, String>, String> getTransitions() {
        return transitions;
    }

    public String getInitialState() {
        return initialState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public boolean isDFA() {
        Set<String> seenTransitions = new HashSet<>();
        for (Map.Entry<Map<String, String>, String> entry : transitions.entrySet()) {
            Map<String, String> fromStateAndSymbol = entry.getKey();
            String fromState = fromStateAndSymbol.keySet().iterator().next();
            String symbol = fromStateAndSymbol.get(fromState);
            String transitionKey = fromState + "," + symbol;
            if (seenTransitions.contains(transitionKey)) {
                return false;
            }
            seenTransitions.add(transitionKey);
        }
        return true;
    }

    public boolean acceptsSequence(String sequence) {
        String currentState = initialState;
        for (char symbol : sequence.toCharArray()) {
            String symbolStr = String.valueOf(symbol);
            Map<String, String> key = new HashMap<>();
            key.put(currentState, symbolStr);
            if (!transitions.containsKey(key)) {
                return false;
            }
            currentState = transitions.get(key);
        }
        return finalStates.contains(currentState);
    }
}
