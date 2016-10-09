package com.dodopal.oss.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.CrdSysOrderBean;
import com.dodopal.oss.business.bean.query.CrdSysOrderQuery;

public interface CrdSysOrderService {
    public DodopalResponse<DodopalDataPage<CrdSysOrderBean>> findCrdSysOrderByPage(CrdSysOrderQuery crdSysOrderQuery);

    public DodopalResponse<CrdSysOrderBean> findCrdSysOrderByCode(String crdOrderNum);

    /**
     * 列表画面的 导出按钮 处理
     * @param response HttpServletResponse
     * @return 导出结果
     */
    public DodopalResponse<List<CrdSysOrderBean>> excelExport(HttpServletResponse response, CrdSysOrderQuery crdSysOrderQuery);
}
