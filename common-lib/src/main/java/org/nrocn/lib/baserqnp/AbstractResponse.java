package org.nrocn.lib.baserqnp;

/**
 * 统一web端响应请求基础类
 * 只做web端的相应
 * 有响应码
 */
public abstract class AbstractResponse implements IMicroResponsable {






    /**
     * 状态码
     */
    protected  Integer status = ResultCode.FAILURE.code;

    /**
     * 状态码和原因短句
     */
    protected ResultCode resultCode = ResultCode.FAILURE;

    /**
     * 详细信息
     */
    protected String message;

    /**
     * 返回数据
     */
    protected  Object data;


    /**
     * 返回状态码
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 判断是否返回成功
     * @return
     */
    public boolean isSuccess() {
        return resultCode == ResultCode.SUCCESS;
    }


    /**
     * 获取原因短句
     * @return
     */
    public ResultCode getResultCode() {
        return resultCode;
    }


    /**
     * 设置状态码
     * @param resultCode
     * @return
     */
    public AbstractResponse setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.status = resultCode.code;
        return this;
    }

    /**
     * 设置详细信息
     * @return
     */
    public String getMessage() {
        return message;
    }



    public AbstractResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public AbstractResponse setData(Object data) {
        this.data = data;
        return this;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "resultCode=" + resultCode +
                ", message='" + message + '\'' +
                '}';
    }
}
