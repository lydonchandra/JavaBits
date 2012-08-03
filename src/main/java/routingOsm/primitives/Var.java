package routingOsm.primitives;

public abstract interface Var {
    public abstract void writeToStream( OutStream paramOutStream );
    public abstract Var readFromStream( InStream paramInStream );
    public abstract boolean isOverridableBy( Var paramVar );
}
