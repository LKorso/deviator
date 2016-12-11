package com.sdr;

import org.rosuda.JRI.Rengine;

public class SimpleRExample {

    
    private Rengine engine;

    public SimpleRExample() {
        engine = new Rengine(new String[] { "--no-save" }, false, null);
    }

    public Double calculateMEAN() {
        // Create an R vector in the form of a string.
        String javaVector = "c(1,2,3,4,5)";

        if (!engine.waitForR()) {
            System.out.println("Cannot load R");
            return null;
        }

        // The vector that was created in JAVA context is stored in 'rVector' which is a variable in R context.
        engine.eval("rVector=" + javaVector);

        //Calculate MEAN of vector using R syntax.
        engine.eval("meanVal=mean(rVector)");

        //Retrieve MEAN value
        Double result = engine.eval("meanVal").asDouble();
        return result;
    }
}
