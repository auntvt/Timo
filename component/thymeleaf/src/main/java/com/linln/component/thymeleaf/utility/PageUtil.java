package com.linln.component.thymeleaf.utility;

import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 分类显示辅助工具对象
 * @author 小懒虫
 * @date 2018/8/14
 */
public class PageUtil {

    private String paramHref;

    public boolean pageInit(Page page, HttpServletRequest request){
        if (page.getTotalElements() > page.getSize() && page.getNumberOfElements() != 0){

            // 获取分页参数地址
            String servletPath = request.getServletPath();
            StringBuffer param = new StringBuffer(servletPath + "?");
            Enumeration em = request.getParameterNames();
            while (em.hasMoreElements()) {
                String name = (String) em.nextElement();
                if(!"page".equals(name)){
                    String value = request.getParameter(name);
                    param.append(name + "=" + value + "&");
                }
            }
            this.paramHref = param.toString();
            return true;
        }
        return false;
    }

    public List<String> pageCode(Page page){
        int number = page.getNumber()+1;
        int totalPages = page.getTotalPages();
        int start = 0;
        int length = 7;
        int half = length % 2 == 0 ? length / 2 : length / 2 + 1;
        List<String> codeList = new ArrayList<>();

        if(totalPages > length && number > half){
            start = number - half;
        }
        if(totalPages > length && number > totalPages - half){
            start = totalPages - length;
        }
        for (int i=1; i <= (totalPages > length ? length : totalPages); i++){
            codeList.add(String.valueOf( i + start));
        }
        if(totalPages > length && number > half){
            codeList.set(0, "1");
            codeList.set(1, "…");
        }
        if(totalPages > length && number < totalPages - (half-1)){
            codeList.set(length-2, "…");
            codeList.set(length-1, String.valueOf(totalPages));
        }
        return codeList;
    }

    public String pageActive(Page page, String pageCode, String className){
        int number = page.getNumber();
        if(!"…".equals(pageCode)){
            if(number == Integer.valueOf(pageCode) - 1){
                return " "+className;
            }
        }
        return "";
    }

    public boolean isPrevious(Page page){
        return page.getNumber() != 0;
    }

    public boolean isNext(Page page){
        return page.getNumber() != page.getTotalPages() - 1;
    }

    public boolean isCode(String pageCode){
        return "…".equals(pageCode);
    }

    public String pageHref(String pageCode){
        return paramHref + "page=" + pageCode;
    }
}
