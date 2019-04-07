package com.linln.common.xss;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Xss防护过滤处理
 * @author 小懒虫
 * @date 2018/12/9
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        Whitelist whitelist = Whitelist.relaxed();

        String[] params = super.getParameterValues(name);
        if(params != null){
            for (int i=0; i<params.length; i++) {
                params[i] = Jsoup.clean(params[i], whitelist).trim();
            }
        }
        return params;
    }
}