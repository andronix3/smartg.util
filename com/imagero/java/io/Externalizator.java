package com.imagero.java.io;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Externalizator helps to support versioning in Externalizable classes.
 * Extending classes should define two public static fields:
 * int FIRST_VERSION and int CURRENT_VERSION
 * 
 * Just use it as superclass.
 * 
 * Extending classs should define method
 *  
 * public void writeExt0(ObjectOutput out)
 * 
 * and method(s)
 * 
 * public void readExtXX(ObjectInput in) for each version (where 'XX' is version number).
 * 
 * @author andrey
 *
 */
public class Externalizator implements Externalizable {
    
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	try {
	    ExternalizableHelper helper = new ExternalizableHelper(this);
	    helper.readExt(in);
	} catch (Throwable t) {
	    throw new IOException(t);
	}
    }

    public void writeExternal(ObjectOutput out) throws IOException {
	try {
	    ExternalizableHelper helper = new ExternalizableHelper(this);
	    helper.writeExt(out);
	} catch (Throwable t) {
	    throw new IOException(t);
	}
    }
}
