package com.itauge.springbootshirobackend.util;

import com.alibaba.fastjson.JSONObject;
import com.itauge.springbootshirobackend.entity.Article;
import com.itauge.springbootshirobackend.util.constants.Constants;
import com.itauge.springbootshirobackend.util.constants.ErrorEnum;
import com.itauge.springbootshirobackend.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    /**
     * 返回一个info为空对象的成功消息的json
     */
    public static ResultVO successJSON() {
        return successJSON(null);
    }

    public static ResultVO successJSON(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(Constants.SUCCESS_CODE);
        resultVO.setMsg(Constants.SUCCESS_MSG);
        resultVO.setData(object);
        return resultVO ;
    }

    /**
     * 返回錯誤信息JSON
     */
    public static ResultVO errorJson(ErrorEnum errorEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(errorEnum.getErrorCode());
        resultVO.setMsg(errorEnum.getErrorMsg());
        resultVO.setData(null);
        return resultVO;
    }

    /**
     * 将request参数值转为json
     * 需要導入maven依賴：fastjson
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 查詢分頁結果后的封裝工具方法
     * @param requestJson
     * @param list
     * @param totalCount
     * @return
     */
    public static ResultVO successPage(final JSONObject requestJson, List list, int totalCount){
        int pageSize = requestJson.getIntValue("pageSize");
        int totalPage = getPageCounts(pageSize,totalCount);
        ResultVO resultVO = CommonUtil.successJSON();
        Map map = new HashMap();
        map.put("list",list);
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        resultVO.setData(map);
        return resultVO;
    }

    /**
     * 查询分页结果后的封装工具方法
     * @param list 查询分页对象list
     */
    public static ResultVO successPage(List<JSONObject> list) {
        JSONObject info = new JSONObject();
        info.put("list", list);
        ResultVO resultVO = new ResultVO();
        resultVO.setData(info);
        return resultVO;
    }

    /**
     * 獲取縂頁數
     * @param pageSize   每页行数
     * @param itemCount 结果的总条数
     */
    private static int getPageCounts(int pageSize, int itemCount) {
        if (itemCount == 0) {
            return 1;
        }
        return itemCount % pageSize > 0 ?
                itemCount / pageSize + 1 :
                itemCount / pageSize;
    }

    /**
     * 在分頁查詢之前，為查詢條件裏加上分頁參數
     * @param jsonObject
     * @param defaultPageSize
     */
    public static void fillPageParam(final JSONObject jsonObject, int defaultPageSize){
        int pageNum = jsonObject.getIntValue("pageNum");//獲取當前頁
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageSize =jsonObject.getIntValue("pageSize");
        pageSize = pageSize == 0 ? defaultPageSize : pageSize;
        jsonObject.put("pageStart",(pageNum-1)*pageSize);//有了當前頁和顯示數就能判斷start
        jsonObject.put("pageSize",pageSize);
        jsonObject.put("pageNum",pageNum);
    }

    /**
     * 分頁查詢之前的處理參數
     * 沒有pageSize參數時,默認每頁10條.
     */
    public static void fillPageParam(final JSONObject paramObject) {
        fillPageParam(paramObject, 10);
    }

//    /**
//     * 验证是否含有全部必填字段
//     *
//     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
//     */
//    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
//        if (!StringTools.isNullOrEmpty(requiredColumns)) {
//            //验证字段非空
//            String[] columns = requiredColumns.split(",");
//            String missCol = "";
//            for (String column : columns) {
//                Object val = jsonObject.get(column.trim());
//                if (StringTools.isNullOrEmpty(val)) {
//                    missCol += column + "  ";
//                }
//            }
//            if (!StringTools.isNullOrEmpty(missCol)) {
//                jsonObject.clear();
//                jsonObject.put("code", ErrorEnum.E_90003.getErrorCode());
//                jsonObject.put("msg", "缺少必填参数:" + missCol.trim());
//                jsonObject.put("info", new JSONObject());
//                throw new CommonJsonException(jsonObject);
//            }
//        }
//    }
}
