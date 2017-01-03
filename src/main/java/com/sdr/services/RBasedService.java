package com.sdr.services;

import org.rosuda.JRI.Rengine;

public class RBasedService {
    protected static Rengine rEngine = new Rengine(new String[] { "--no-save" }, false, null);
}
