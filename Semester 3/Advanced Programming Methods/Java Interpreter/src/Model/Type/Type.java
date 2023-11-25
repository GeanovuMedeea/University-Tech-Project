package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public interface Type {
    BoolValue isEqualWith(Type typeToCheck);
    Value getDefaultValue();
}