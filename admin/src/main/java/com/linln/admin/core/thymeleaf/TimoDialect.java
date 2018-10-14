package com.linln.admin.core.thymeleaf;

import com.linln.admin.core.thymeleaf.attribute.SelectDictEnumAttrProcessor;
import com.linln.admin.core.thymeleaf.attribute.SelectDictKeyAttrProcessor;
import com.linln.admin.core.thymeleaf.attribute.SelectListAttrProcessor;
import com.linln.admin.core.thymeleaf.attribute.UserAttrProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
public class TimoDialect extends AbstractProcessorDialect implements IExpressionObjectDialect {

    private static final String NAME = "TimoDialect";
    private static final String PREFIX = "mo";
    private IExpressionObjectFactory expressionObjectFactory = null;

    public TimoDialect() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        LinkedHashSet processors = new LinkedHashSet();
        processors.add(new UserAttrProcessor(dialectPrefix));
        processors.add(new SelectDictEnumAttrProcessor(TemplateMode.HTML, dialectPrefix));
        processors.add(new SelectDictKeyAttrProcessor(TemplateMode.HTML, dialectPrefix));
        processors.add(new SelectListAttrProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        if (this.expressionObjectFactory == null) {
            this.expressionObjectFactory = new TimoExpressionObjectFactory();
        }
        return this.expressionObjectFactory;
    }
}
