package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value {
    private final int val;
    private final Type locationType;

    public RefValue(int valToSet, Type locationTypeToSet) {
        val = valToSet;
        locationType=locationTypeToSet;
    }

    public int getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return String.format("Address: %d Type: %s", val,locationType.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RefValue castObj))
            return false;
        return val == castObj.val && locationType.isEqualWith(castObj.getType()).getVal();
    }
}
