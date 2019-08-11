package com.linln.component.excel;

import com.linln.component.excel.enums.ExcelType;
import com.linln.component.excel.annotation.Excel;
import com.linln.common.utils.EhCacheUtil;
import com.linln.common.utils.HttpServletUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 小懒虫
 * @date 2018/11/8
 */
public class ExcelUtil {

    private static int dataRow = 2;
    private static Cache dictCache = EhCacheUtil.getDictCache();

    /**
     * 获取通用样式
     */
    private static XSSFCellStyle getCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        XSSFFont font = workbook.createFont();
        font.setFontName("Microsoft YaHei UI");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 功能模板（标题及表头）
     */
    private static XSSFWorkbook getCommon(String sheetTitle, List<Field> fields) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetTitle);

        // 设置列宽度
        for (int i = 0; i < fields.size(); i++) {
            sheet.setColumnWidth(i, 16 * 256);
        }

        // 通用样式
        XSSFCellStyle cellStyle = getCellStyle(workbook);

        // 标题样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.cloneStyleFrom(cellStyle);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontName(cellStyle.getFont().getFontName());
        titleFont.setBold(true);
        titleFont.setFontHeight(14);
        titleStyle.setFont(titleFont);

        // 表头样式
        XSSFCellStyle thStyle = workbook.createCellStyle();
        thStyle.cloneStyleFrom(titleStyle);
        thStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        thStyle .setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        XSSFFont thFont = workbook.createFont();
        thFont.setFontName(cellStyle.getFont().getFontName());
        thFont.setBold(titleFont.getBold());
        thFont.setColor(IndexedColors.WHITE.getIndex());
        thStyle.setFont(thFont);

        // 创建标题样式、表格表头
        XSSFRow titleRow = sheet.createRow(0);
        XSSFRow thsRow = sheet.createRow(1);
        for (int i = 0; i < fields.size(); i++) {
            Excel excel = fields.get(i).getAnnotation(Excel.class);
            XSSFCell title = titleRow.createCell(i);
            title.setCellStyle(titleStyle);
            XSSFCell th = thsRow.createCell(i);
            th.setCellValue(excel.value());
            th.setCellStyle(thStyle);
        }

        // 绘制标题
        titleRow.setHeight((short) (26 * 20));
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(sheetTitle);
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, fields.size() - 1));
        return workbook;

    }

    /**
     * 获取实体类带有@Excel的属性
     */
    private static List<Field> getExcelList(Class<?> entity, ExcelType type) {
        List<Field> list = new ArrayList<>();
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Excel.class)) {
                ExcelType fieldType = field.getAnnotation(Excel.class).type();
                if(fieldType.equals(ExcelType.ALL) || fieldType.equals(type)){
                    list.add(field);
                }
            }
        }
        return list;
    }

    /**
     * 获取实体类带有@Excel字段名
     */
    private static List<String> getFieldName(List<Field> fields) {
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            list.add(field.getName());
        }
        return list;
    }

    /**
     * 下载操作
     */
    private static void download(XSSFWorkbook workbook, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName + ".xlsx", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpServletResponse response = HttpServletUtil.getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        OutputStream ros = null;
        try {
            ros = response.getOutputStream();
            workbook.write(ros);
            ros.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ros != null){
                try {
                    ros.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取Excel模板
     * @param entity 实体类Class
     */
    public static void genTemplate(Class<?> entity) {
        Excel excel = entity.getAnnotation(Excel.class);
        if (excel != null) {
            genTemplate(entity, excel.value());
        } else {
            genTemplate(entity, entity.getSimpleName());
        }
    }

    /**
     * 获取Excel模板
     * @param entity     实体类Class
     * @param sheetTitle 工作组标题（文件名称）
     */
    public static void genTemplate(Class<?> entity, String sheetTitle) {
        XSSFWorkbook workbook = getCommon(sheetTitle, getExcelList(entity, ExcelType.IMPORT));
        download(workbook, sheetTitle + "模板");
    }

    /**
     * 导出Excel数据
     * @param entity 实体类Class
     * @param list   导出的数据列表
     */
    public static <T> void exportExcel(Class<?> entity, List<T> list) {
        Excel excel = entity.getAnnotation(Excel.class);
        if (excel != null) {
            exportExcel(entity, list, excel.value());
        } else {
            exportExcel(entity, list, entity.getSimpleName());
        }
    }

    /**
     * 导出Excel数据
     * @param entity     实体类Class
     * @param list       导出的数据列表
     * @param sheetTitle 工作组标题（文件名称）
     */
    public static <T> void exportExcel(Class<?> entity, List<T> list, String sheetTitle) {
        List<Field> fields = getExcelList(entity, ExcelType.EXPORT);
        List<String> fns = getFieldName(fields);

        XSSFWorkbook workbook = getCommon(sheetTitle, fields);
        XSSFSheet sheet = workbook.getSheet(sheetTitle);
        XSSFCellStyle cellStyle = getCellStyle(workbook);

        // 时间样式
        XSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(cellStyle);
        XSSFDataFormat format = workbook.createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));

        for (int i = 0; i < list.size(); i++) {
            XSSFRow row = sheet.createRow(i + dataRow);
            T item = list.get(i);

            // 通过反射机制获取实体对象的状态
            try {
                final BeanInfo bi = Introspector.getBeanInfo(item.getClass());
                for (final PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                    if (fns.contains(pd.getName())) {
                        Object value = pd.getReadMethod().invoke(item, (Object[]) null);
                        int index = fns.indexOf(pd.getName());
                        XSSFCell cell = row.createCell(index);
                        if (value != null) {
                            Excel excel = fields.get(index).getAnnotation(Excel.class);
                            // 字典值转换
                            String dict = excel.dict();
                            if (!dict.isEmpty()){
                                Element dictEle = dictCache.get(dict);
                                if(dictEle != null){
                                    @SuppressWarnings("unchecked") Map<String, String> dictValue = (Map<String, String>) dictEle.getObjectValue();
                                    value = dictValue.get(String.valueOf(value));
                                }
                            }

                            // 获取关联对象指定的值
                            String joinField = excel.joinField();
                            if (!joinField.isEmpty()){
                                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(value.getClass(), joinField);
                                value = sourcePd.getReadMethod().invoke(value, (Object[]) null);
                            }

                            // 给单元格赋值
                            if (value instanceof Number) {
                                cell.setCellValue((Double.valueOf(String.valueOf(value))));
                            } else if (value instanceof Date) {
                                cell.setCellValue((Date) value);
                                cell.setCellStyle(dateStyle);
                                continue;
                            } else {
                                cell.setCellValue(String.valueOf(value));
                            }
                        }
                        cell.setCellStyle(cellStyle);
                    }
                }
            } catch (InvocationTargetException | IntrospectionException | IllegalAccessException e) {
                String message = "导入失败：字段名称匹配失败！";
                throw new IllegalArgumentException(message, e);
            }

        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        download(workbook, sheetTitle + dateFormat.format(new Date()));
    }

    /**
     * 读取Excel文件数据
     * @param entity 实体类
     * @param inputStream Excel文件输入流
     * @return 返回数据集合
     */
    public static <T> List<T> importExcel(Class<T> entity, InputStream inputStream){
        List<T> list = new ArrayList<>();
        List<String> fns = getFieldName(getExcelList(entity, ExcelType.IMPORT));

        // 读取Excel文件
        XSSFWorkbook workbook = null;
        try {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.notNull(workbook, "该取Excel文件失败，请检查文件！");
        XSSFSheet sheet = workbook.getSheetAt(0);
        Assert.notNull(sheet, "该Excel文件没有工作区，无法读取数据！");
        int count = 0;
        for (Row row : sheet) {

            // 通过非数据行
            if(count < dataRow ) {
                count++;
                continue;
            }

            // 读取当前行数据
            int end = row.getLastCellNum();
            String[] rowData = new String[end];
            for (int i = 0; i < end; i++) {
                Cell cell = row.getCell(i);
                if(cell != null){
                    if(cell.getCellType() == CellType.NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)){
                        Date date = cell.getDateCellValue();
                        rowData[i] = String.valueOf(date.getTime());
                    }else {
                        // 强制其他类型为STRING字符串类型
                        cell.setCellType(CellType.STRING);
                        rowData[i] = cell.getStringCellValue();
                    }
                }else {
                    rowData[i] = null;
                }
            }

            // 将数据添加到数据列表中
            try {
                T newInstance = entity.newInstance();
                final BeanInfo bi = Introspector.getBeanInfo(entity);
                for (final PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                    if (fns.contains(pd.getName())) {
                        Method writeMethod = pd.getWriteMethod();
                        if(writeMethod != null){
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            String value = rowData[fns.indexOf(pd.getName())];
                            if(!StringUtils.isEmpty(value)){
                                Class<?> propertyType = pd.getPropertyType();
                                if (String.class == propertyType){
                                    writeMethod.invoke(newInstance, value);
                                }else if(Integer.class == propertyType){
                                    writeMethod.invoke(newInstance, Integer.valueOf(value));
                                }else if(Long.class == propertyType){
                                    writeMethod.invoke(newInstance, Double.valueOf(value).longValue());
                                }else if(Float.class == propertyType){
                                    writeMethod.invoke(newInstance, Float.valueOf(value));
                                }else if(Short.class == propertyType){
                                    writeMethod.invoke(newInstance, Short.valueOf(value));
                                }else if(Double.class == propertyType){
                                    writeMethod.invoke(newInstance, Double.valueOf(value));
                                }else if(Character.class == propertyType){
                                    if ((value != null) && (value.length() > 0)){
                                        writeMethod.invoke(newInstance, Character.valueOf(value.charAt(0)));
                                    }
                                }else if(Date.class == propertyType){
                                    writeMethod.invoke(newInstance, new Date(Long.parseLong(value)));
                                }else if(BigDecimal.class == propertyType){
                                    writeMethod.invoke(newInstance, new BigDecimal(value));
                                }
                            }
                        }
                    }
                }
                list.add(newInstance);
            } catch (InvocationTargetException | IntrospectionException | IllegalAccessException e) {
                String message = "导入失败：字段名称匹配失败！";
                throw new IllegalArgumentException(message, e);
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
