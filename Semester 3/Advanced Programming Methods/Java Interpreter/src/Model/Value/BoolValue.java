package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value {
    private final boolean val;

    public BoolValue(boolean valToSet) {
        val = valToSet;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return val ? "true" : "false";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoolValue castObj))
            return false;
        return val == castObj.val;
    }
}