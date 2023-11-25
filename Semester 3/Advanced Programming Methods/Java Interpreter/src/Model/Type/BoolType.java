package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type {
    @Override
    public BoolValue isEqualWith(Type typeToCheck) {
        return new BoolValue(typeToCheck instanceof BoolType);
    }
    @Override
    public BoolValue getDefaultValue() {
        return new BoolValue(false);
    }
    @Override
    public String toString() {
        return "bool";
    }
}