package com.example.ruler.noteapplication.Data;

import java.io.Serializable;

public class BasePatch implements Serializable {
    public int patchCode;
    public String patchMessage;
    public PatchInfo data;
}
