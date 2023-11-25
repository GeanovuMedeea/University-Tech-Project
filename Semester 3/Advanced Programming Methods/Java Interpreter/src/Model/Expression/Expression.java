package Model.Expression;

import Collection.Dictionary.MyIDictionary;
import Model.Exceptions.*;
import Model.Type.Type;
import Model.Value.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String, Value> symbolTable) throws ToyLanguageInterpreterException;
    String toString();
}