package org.acgcloud.config.config;

import lombok.Data;
import org.acgcloud.config.services.IAcgCloudConfig;

import java.io.Serializable;


@Data
public abstract class AbstractAppConfig  implements IAcgCloudConfig,Serializable {
    protected  Integer port;


}
