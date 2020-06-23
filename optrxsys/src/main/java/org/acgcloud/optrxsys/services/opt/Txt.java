package org.acgcloud.optrxsys.services.opt;

import org.springframework.stereotype.Component;

import java.io.InputStream;


@Component
public class Txt extends AbstractFileOpt {



    @Override
    public String support() {
        return "txt";
    }

    @Override
    public void handle() {

    }

    @Override
    public InputStream open() {
        return  this.ioRefer;
    }
}
