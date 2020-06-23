package org.acgcloud.optrxsys.services;

import lombok.SneakyThrows;
import org.acgcloud.optrxsys.dto.OptrxRequest;

import java.io.File;
import java.net.URL;

public interface FileOpenServices {

    @SneakyThrows
    File getResourceByTcp(URL url, String target);

    IRxFileOptStragy parse(OptrxRequest optrxRequest);

}
