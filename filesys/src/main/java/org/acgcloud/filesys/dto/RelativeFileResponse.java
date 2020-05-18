package org.acgcloud.filesys.dto;

import lombok.Data;
import org.nrocn.lib.baseobj.RelativeFileObj;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.Responsable;

import java.io.Serializable;
import java.util.List;

@Data
public class RelativeFileResponse implements Responsable, Serializable {


    private static final long serialVersionUID = 1L;

    private String parent;

    private String current;

    private List<RelativeFileObj> relativeFileObjs;


    public RelativeFileResponse() {
    }

    public RelativeFileResponse(String parent, List<RelativeFileObj> relativeFileObjs) {
        this.parent = parent;
        this.relativeFileObjs = relativeFileObjs;
    }


    @Override
    public String toString() {
        return "RelativeFileResponse{" +
                "parent='" + parent + '\'' +
                ", relativeFileObjs=" + relativeFileObjs +
                '}';
    }
}
