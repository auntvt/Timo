package com.linln.admin.core.thymeleaf.attribute;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * 自定义用户信息展示标签
 * @author 小懒虫
 * @date 2018/8/14
 */
public class UserAttrProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTRIBUTE_NAME = "user";
    private static final int PRECEDENCE = 300;

    public UserAttrProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                null, // No tag name: match any tag name
                false, // No prefix to be applied to tag name
                ATTRIBUTE_NAME, // Name of the attribute that will be matched
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true); // Remove the matched attribute afterwards
    }


    @Override
    protected void doProcess(ITemplateContext iTemplateContext,
                             IProcessableElementTag iProcessableElementTag,
                             AttributeName attributeName,
                             String attributeValue,
                             IElementTagStructureHandler iElementTagStructureHandler) {

        // 通过反射机制获取Shiro主体对象属性值
        String text = null;
        Object principal = SecurityUtils.getSubject().getPrincipal();
        try {
            final BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
            for (final PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                if (pd.getName().equals(attributeValue)) {
                    final Object value = pd.getReadMethod().invoke(principal, (Object[]) null);
                    text = String.valueOf(value);
                }
            }
            if( text == null ){
                throw new IllegalArgumentException("Property [" + attributeValue + "] not found in principal of type [" + principal.getClass().getName() + "]");
            }
        } catch (final Exception e) {
            String message = "Error reading property [" + attributeValue + "] from principal of type [" + principal.getClass().getName() + "]";
            throw new IllegalArgumentException(message, e);
        }

        iElementTagStructureHandler.setBody(text, false);
    }
}
