package org.acgcloud.config;


import org.acgcloud.config.annotation.MServer;
import org.acgcloud.config.config.AbstractAcgCloudConfig;
import org.acgcloud.config.services.Configable;


@MServer(serverId = "dmsys")
public class DmsysConfig extends AbstractAcgCloudConfig implements Configable {

}
