package org.nrocn.lib.basecontroller;

import org.nrocn.lib.baserqnp.IMicroResponsable;

public  abstract  class BaseWebController {

    private  final String modelName ;

    public BaseWebController() {
        modelName = this.namedModelName();
    }

    /**
     * 给模块命名
     * @return
     */
    protected abstract String namedModelName();

    protected  abstract  IMicroResponsable modelInfo();


    public String getModelName() {
        return modelName;
    }
}
