package org.acgcloud.optrxsys;

import org.acgcloud.optrxsys.controller.MainSvController;

public abstract class AbstractConstant {

    public static  final String TEMP = "/static/temp";


    public static final String FILE_CONTEXT_PATH = MainSvController.class.getClassLoader()
            .getResource("").getPath();


}
