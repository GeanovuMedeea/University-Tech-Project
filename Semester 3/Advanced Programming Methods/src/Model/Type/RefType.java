package Model.Type;

import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements Type {
    Type inner;
    public RefType(Type inner){this.inner =inner;}
    public Type getInner(){return inner;}
    @Override
    public BoolValue isEqualWith(Type typeToCheck) {
        return new BoolValue((typeToCheck instanceof RefType) && (inner.isEqualWith(((RefType) typeToCheck).getInner())).getVal());
    }
    @Override
    public RefValue getDefaultValue() {
        if(inner instanceof RefType)
            return new RefValue(0,new RefType(inner));
        return new RefValue(0,inner);
    }
    @Override
    public String toString() {
        return "reference("+inner.toString()+")";
    }
}