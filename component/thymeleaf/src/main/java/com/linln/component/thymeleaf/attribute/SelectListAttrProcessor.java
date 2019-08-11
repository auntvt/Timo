package com.linln.component.thymeleaf.attribute;

import lombok.EqualsAndHashCode;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.*;
import org.thymeleaf.templatemode.TemplateMode;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义下拉列表生成标签
 * @author 小懒虫
 * @date 2018/8/14
 */
public class SelectListAttrProcessor extends SelectDictAttrProcessor {

    public static final int PRECEDENCE = 1400;
    public static final String ATTR_NAME = "list";

    public SelectListAttrProcessor(final TemplateMode templateMode, final String dialectPrefix) {
        super(templateMode, dialectPrefix, ATTR_NAME, PRECEDENCE);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doProcess(
            final ITemplateContext context,
            final IProcessableElementTag tag,
            final AttributeName attributeName,
            final String attributeValue,
            final IElementTagStructureHandler structureHandler) {

        // 如果属性值为空或者标签不为select。则不处理
        String elementName = tag.getElementCompleteName();
        if (attributeValue.isEmpty() || !"select".equals(elementName)) {
            return;
        }

        // 获取列表对象
        final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = expressionParser.parseExpression(context, attributeValue);
        Object expressionResult;
        if (expression != null && expression instanceof FragmentExpression) {
            final FragmentExpression.ExecutedFragmentExpression executedFragmentExpression =
                    FragmentExpression.createExecutedFragmentExpression(context, (FragmentExpression) expression);
            expressionResult =
                    FragmentExpression.resolveExecutedFragmentExpression(context, executedFragmentExpression, true);
        } else {
            expressionResult = expression.execute(context, StandardExpressionExecutionContext.RESTRICTED);
            if (expressionResult == NoOpToken.VALUE) {
                return;
            }
        }

        // 转换列表对象
        Map<String, String> valueList = new LinkedHashMap<>();
        if(expressionResult.getClass().isArray()){
            // 转换数组
            int length = Array.getLength(expressionResult);
            for (int i = 0; i < length; i++) {
                String value = String.valueOf(Array.get(expressionResult, i));
                valueList.put(value, value);
            }
        }else if(expressionResult instanceof Collection){
            // 装换Collection集合
            Collection list = (Collection) expressionResult;
            list.forEach(item -> {
                valueList.put(String.valueOf(item), String.valueOf(item));
            });
        }else if(expressionResult instanceof Map){
            // 装换Map集合
            Map list = (Map) expressionResult;
            list.forEach((key, item) -> {
                valueList.put(String.valueOf(key), String.valueOf(item));
            });
        }

        if (valueList.size() > 0) {
            doProcess(context, tag, attributeName, attributeValue, structureHandler, valueList);
        };
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
