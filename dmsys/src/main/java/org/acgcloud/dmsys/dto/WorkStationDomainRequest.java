package org.acgcloud.dmsys.dto;

import lombok.Data;
import org.nrocn.lib.baseobj.AbstractDomain;
import org.nrocn.lib.baserqnp.Requestable;

import java.util.List;

@Data
public class WorkStationDomainRequest extends AbstractDomain implements Requestable {

    private  Long currFolderId;

    private  String fileName;

    private  String[] fileNames;

    private Boolean upload;

    private Boolean isFile;

    private List<Long> ids;

}
