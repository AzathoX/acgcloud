package org.acgcloud.optrxsys.services.opt;

import org.acgcloud.optrxsys.office.IOffices;
import org.acgcloud.optrxsys.office.xls.Excel;
import org.springframework.stereotype.Component;


@Component
public class Xls extends AbstractFileOpt {



    @Override
    public String support() {
        return "excel";
    }

    @Override
    public void handle() {
        String path = file.getAbsolutePath();
        IOffices iOffices = Excel.newInstance(path);
        this.ioRefer = iOffices.toHtml(this.bufferePath);
    }

}
