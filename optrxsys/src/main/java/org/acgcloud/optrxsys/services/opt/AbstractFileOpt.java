package org.acgcloud.optrxsys.services.opt;

import lombok.Data;
import org.acgcloud.optrxsys.services.IRxFileOptStragy;
import org.acgcloud.optrxsys.dto.OptrxRequest;

import java.io.File;
import java.io.InputStream;


@Data
public abstract  class AbstractFileOpt implements IRxFileOptStragy {
    protected File file;

    protected String bufferePath;

    protected OptrxRequest optrxRequest;

    protected InputStream ioRefer;

    @Override
    public abstract String support();


    @Override
    public abstract void handle();

    @Override
    public InputStream open() {
        return ioRefer;
    }

}
