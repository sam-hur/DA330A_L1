package com.da330a.jfx.Data;

public class EndOfWrite extends Exception {
    public EndOfWrite() {
        super("End of write operation reached.");
    }
}