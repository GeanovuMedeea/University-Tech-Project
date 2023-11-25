package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{
    private final String val;

    public StringValue(String valToSet) {
        val = valToSet;
    }

    public String getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StringValue castObj))
            return false;
        return val.equals(castObj.val);
    }
}