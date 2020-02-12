package com.zgw.base.picselector.compress;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamProvider {

    InputStream open() throws IOException;

    void close();

    String getPath();
}
