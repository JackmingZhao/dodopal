package com.dodopal.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dodopal.common.exception.DDPException;

public class Utils {

    public static Object deepClone(Object toClone) {
        ObjectOutputStream out = null;

        ObjectInputStream in = null;

        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            out = new ObjectOutputStream(new BufferedOutputStream(bout));
            out.writeObject(toClone);
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(bout.toByteArray())));
            Object clone = in.readObject();
            return clone;
        }
        catch (IOException e) {
            throw new DDPException("ERROR.DEEP.CLONE", e.getMessage(), e);
        }
        catch (ClassNotFoundException e) {
            throw new DDPException("ERROR.DEEP.CLONE", e.getMessage(), e);
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException ioe) {

                }

            }
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException ioe) {

                }

            }
        }
    }

}
