package com.codecool.nopainnogain.util;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationUtility {
    /** Read the object from Base64 string. */
    public static Object fromString(String s) throws IOException,
            ClassNotFoundException {
        final byte [] data = Base64.decode(s, Base64.DEFAULT);
        final ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        final Object o  = ois.readObject();

        ois.close();

        return o;
    }

    /** Write the object to a Base64 string. */
    public static String toString(Serializable o) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(o);
        oos.close();
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}
