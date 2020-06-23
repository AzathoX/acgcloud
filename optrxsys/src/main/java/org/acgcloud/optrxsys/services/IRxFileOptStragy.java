package org.acgcloud.optrxsys.services;

import org.acgcloud.optrxsys.dto.OptrxRequest;
import org.nrocn.lib.design.IStrategy;

import java.io.File;
import java.io.InputStream;

public interface IRxFileOptStragy extends IStrategy {

    void setOptrxRequest(OptrxRequest optrxRequest);

    void setFile(File file);



    InputStream open();
}
