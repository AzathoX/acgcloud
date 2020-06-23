package org.acgcloud.optrxsys.dto;

import lombok.Data;
import org.nrocn.lib.baseobj.BaseDomain;
import org.nrocn.lib.baserqnp.IMicroRequestable;
import org.acgcloud.config.config.AbstractAppConfig;
import org.acgcloud.config.config.AbstractAcgCloudConfig;
import org.acgcloud.config.config.AppConfiguration;

import java.io.File;
import java.io.Serializable;
import java.util.Map;


@Data
public class OptrxRequest extends BaseDomain implements IMicroRequestable , Serializable {
    private static final long serialVersionUID = 1L;

    private File file;

    private String path;

    private String originName;

    private String opt;

    private String bufferPath;

    private String url;

    private Map<String,Object> param;

}
