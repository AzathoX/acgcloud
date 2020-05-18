package org.acgcloud.common.dto;

import org.nrocn.lib.baserqnp.AbstractResponse;
import org.nrocn.lib.baserqnp.IMicroResponsable;
import org.nrocn.lib.baserqnp.ResultCode;


public class WebResponse extends AbstractResponse {

    private static final long serialVersionUID = 1L;


    private static final WebResponse WEB_RESPONSE = new WebResponse();


    private WebResponse(){ }




    public static IMicroResponsable getPrototype(){
        return  new WebResponse();
    }

    public static IMicroResponsable getSingleton(){
        return WEB_RESPONSE;
    }




    @Override
    public IMicroResponsable successResp(String successDetail, Object data) {
        this.setMessage(successDetail);
        this.setData(data);
        this.setResultCode(ResultCode.SUCCESS);
        return this;
    }

    @Override
    public IMicroResponsable failedResp(String failDetail, ResultCode code) {
        this.setResultCode(code);
        this.setMessage(failDetail);
        return this;
    }

    @Override
    public IMicroResponsable failedResp(String failDetail, ResultCode code , Object data) {
        this.setResultCode(code);
        this.setMessage(failDetail);
        this.setData(data);
        return this;
    }


    @Override
    public String toString() {
        return "WebResponse{" +
                "status=" + status +
                ", resultCode=" + resultCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
