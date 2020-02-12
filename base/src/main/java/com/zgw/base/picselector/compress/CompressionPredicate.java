package com.zgw.base.picselector.compress;



public interface CompressionPredicate {

    /**
     * Determine the given input path should be compressed and return a boolean.
     * @param path input path
     * @return the boolean result
     */
    boolean apply(String path);
}
