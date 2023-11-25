package Model.Type;

import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type {
    @Override
    public BoolValue isEqualWith(Type typeToCheck) {
        return new BoolValue(typeToCheck instanceof StringType);
    }
    @Override
    public StringValue getDefaultValue() {
        return new StringValue("");
    }
    @Override
    public String toString() {
        return "string";
    }
}