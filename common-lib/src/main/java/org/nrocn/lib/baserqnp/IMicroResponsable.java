package org.nrocn.lib.baserqnp;

import java.io.Serializable;

/**
 * 微响应接口
 * 一个成功响应
 * 一个失败响应
 */
public interface IMicroResponsable extends Responsable,Serializable  {

    /**
     * 快速返回一个成功的响应对象成功只有200
     * @param successDetail 详情
     * @param data 数据
     * @return
     */
    IMicroResponsable successResp(String successDetail, Object data);

    /**
     *快速返回一个失败的响应对象失败情况比较多
     * @param failDetail 详情
     * @param code 原因短句
     * @return
     */
    IMicroResponsable failedResp(String failDetail, ResultCode code);

    /**
     *快速返回一个失败的响应对象失败情况比较多
     * @param failDetail 详情
     * @param code 原因短句
     * @return
     */
    IMicroResponsable failedResp(String failDetail, ResultCode code,Object data);
}
