package com.imagero.java.io;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Used by Externalizator.
 * @author andrey
 *
 */
final class ExternalizableHelper {

    Object obj;

    int firstVersion;
    int currentVersion;

    ExternalizableHelper(Object obj) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	this.obj = obj;

	Field f = obj.getClass().getField("FIRST_VERSION");
	firstVersion = f.getInt(null);

	f = obj.getClass().getField("CURRENT_VERSION");
	currentVersion = f.getInt(null);
    }

    void writeExt(ObjectOutput out) throws IOException {
	out.writeInt(currentVersion);
	try {
	    Method m = obj.getClass().getMethod("writeExt0", new Class[] { ObjectOutput.class });
	    m.invoke(obj, new Object[] { out });
	} catch (Throwable ex) {
	    throw new IOException(ex);
	}
    }

    void readExt(ObjectInput in) throws IOException {
	int version = in.readInt();
	if (version < firstVersion) {
	    throw new IOException("Corrupt data stream.");
	}
	if (version > currentVersion) {
	    throw new IOException("Can't read from future.");
	}

	try {
	    Method m = obj.getClass().getMethod("readExt" + version, new Class[] { ObjectInput.class });
	    m.invoke(obj, new Object[] { in });
	} catch (Throwable ex) {
	    throw new IOException(ex);
	}
    }
}
