package org.acgcloud.dmsys.dto;

import lombok.Data;
import org.nrocn.lib.baseobj.AbstractDomain;
import org.nrocn.lib.baserqnp.Requestable;

@Data
public class WorkStationDomainRequest extends AbstractDomain implements Requestable {

    private  Long currFolderId;

    private  String fileName;



    private Boolean isFile;

}
