package Model.Value;

import Model.Type.Type;
import Model.Type.IntType;

public class IntValue implements Value {
    private final int val;

    public IntValue(int valToSet) {
        val = valToSet;
    }

    public int getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.format("%d", val);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntValue castObj))
            return false;
        return val == castObj.val;
    }
}