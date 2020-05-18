package org.nrocn.lib.baserqnp;

import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public enum ResultCode {

    /**
     * 请求成功
     */
    SUCCESS(HttpServletResponse.SC_OK, "Operation is Successful"),

    /**
     * 请求失败
     */
    FAILURE(HttpServletResponse.SC_BAD_REQUEST, "Biz Exception"),

    /**
     * 未通过认证
     */
    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "Request Unauthorized"),

    /**
     * 没有找到
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 Not Found"),

    /**
     * 错误请求
     */
    MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "Message Can't be Read"),

    /**
     * 不支持方法
     */
    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method Not Supported"),

    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Media Type Not Supported"),

    /**
     * 拒绝访问
     */
    REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "Request Rejected"),

    /**
     * 服务错误
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error"),

    /**
     * 参数错误
     */
    PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "Missing Required Parameter"),

    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Type Mismatch"),

    /**
     * 参数绑定错误
     */
    PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Binding Error"),

    /**
     * 参数校验错误
     */
    PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Validation Error");

    final int code;

    final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
