package Model.Type;

import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Model.Value.IntValue;

public class IntType implements Type {
    @Override
    public BoolValue isEqualWith(Type typeToCheck) {
        return new BoolValue(typeToCheck instanceof IntType);
    }
    @Override
    public IntValue getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type getInner() {
        return null;
    }

    @Override
    public String toString() {
        return "int";
    }
}