package ru.kpfu.itis.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public class StreamUtils {
    public static float readFloat(DataInputStream stream) {
        try {
            return stream.readFloat();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static short readShort(DataInputStream stream) {
        try {
            return stream.readShort();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte readByte(DataInputStream stream) {
        try {
            return stream.readByte();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void writeShort(DataOutputStream stream, short num) {
        try {
            stream.writeShort(num);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void writeByte(DataOutputStream stream, byte b) {
        try {
            stream.writeByte(b);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void writeFloat(DataOutputStream stream, float num) {
        try {
            stream.writeFloat(num);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
