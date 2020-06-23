package org.acgcloud.optrxsys.services.opt;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.acgcloud.optrxsys.office.IOffices;
import org.acgcloud.optrxsys.office.docx.Word;


@Component
public class Dox extends AbstractFileOpt {



    @Override
    public String support() {
        return "word";
    }



    @SneakyThrows
    @Override
    public void handle() {

        String path = file.getAbsolutePath();
        IOffices iOffices = Word.newInstance(path);
        this.ioRefer = iOffices.toHtml(this.bufferePath);
    }


}
