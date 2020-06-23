package org.acgcloud.optrxsys.services.opt;

import org.springframework.stereotype.Component;
import org.acgcloud.optrxsys.office.IOffices;
import org.acgcloud.optrxsys.office.ppt.PowerPoint;


@Component
public class Ppt extends AbstractFileOpt {



    @Override
    public String support() {
        return "powerpoint";
    }

    @Override
    public void handle() {
        String path = file.getAbsolutePath();
        IOffices iOffices = PowerPoint.newInstance(path);
        this.ioRefer = iOffices.toHtml(this.bufferePath);
    }


}
