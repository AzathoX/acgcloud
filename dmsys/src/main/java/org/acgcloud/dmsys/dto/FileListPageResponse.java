package org.acgcloud.dmsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileListPageResponse {

    Integer listCount;

    List<CloudFlodlerDomain> page;

}
