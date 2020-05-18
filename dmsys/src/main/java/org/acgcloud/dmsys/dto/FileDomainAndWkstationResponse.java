package org.acgcloud.dmsys.dto;

import lombok.Data;
import org.acgcloud.dmsys.model.CloudFlodlerDomain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FileDomainAndWkstationResponse {

    private Long parentId;

    private Set<Long> includeId;

    private List<CloudFlodlerDomain> cloudFlodlerDomains;

    public  FileDomainAndWkstationResponse(){
        includeId = new HashSet<>();
        cloudFlodlerDomains = new ArrayList<>();
    }

    public void addIncludeId(Long id){
         includeId.add(id);
    }

    public void addCloudFloderDomains(CloudFlodlerDomain cloudFlodlerDomain){
        cloudFlodlerDomains.add(cloudFlodlerDomain);
    }


    public  void addCloudFloderAndIncludeId(Long id , CloudFlodlerDomain cloudFlodlerDomain){
        addIncludeId(id);
        addCloudFloderDomains(cloudFlodlerDomain);
    }
}
