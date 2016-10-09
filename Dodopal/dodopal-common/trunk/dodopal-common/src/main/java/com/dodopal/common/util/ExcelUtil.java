package com.dodopal.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.model.ExcelModel;

public class ExcelUtil {
	private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    /** EXCEL模板保存路径 */
	private static final String FILEPATH = DodopalAppVarPropsUtil.getStringProp(CommonConstants.EXCEL_TEMPLATE_FILE_PATH);
	private static final String FILE_SEPARATOR = "/";
	// 文件后缀名
	private static final String FILE_SUFFIX = ".xlsx";
	// 读取的模板名称
	private static final String DEFAULT_TEMPLATE_NAME = "commonTemplate";
	// 默认数据行高
	private static final int DEFAULT_DATA_ROW_HEIGHT = 18;
	// 默认列宽
	private static final int DEFAULT_COLUMN_WIDTH = 20;
	// 默认标题行标
	private static final int DEFAULT_TITLE_NUM = 0;
	// 默认列标题行标
	private static final int DEFAULT_COLUMN_TITLE_NUM = 2;
	// 默认数据开始行标
	private static final int DEFAULT_DATA_START_INDEX = 3;
	// 默认生成sheet名称
	private static final String DEFAULT_SHEET_NAME = "sheet1";
	//导出最大条数
	public static final int EXPORT_MAX_COUNT = 5000;

	/**
	 * 得到excel 表格中的单元格值
	 */
	public static String getValue(Cell cell) {
		
		if (cell == null) {
			return "";
		}
		if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			// return the pure string value ;
			DecimalFormat df = new DecimalFormat("0");
			return df.format(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}

	public static Date getDateValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		
		if(Cell.CELL_TYPE_NUMERIC == cell.getCellType() && HSSFDateUtil.isCellDateFormatted(cell)){
			return cell.getDateCellValue();
		}

		String value = getValue(cell);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		
		if (StringUtils.isNotBlank(value)) {
			try {
				if(value.indexOf("-") != -1){
					Date returnDate = sdf.parse(value);
					
					if(!value.equals(sdf.format(returnDate))){
						throw new DDPException("parse.date.error","日期格式错误");
					}
					
					return returnDate;
				}
				
				if(value.indexOf("/") != -1){
					Date returnDate = sdf2.parse(value);
					
					if(!value.equals(sdf2.format(returnDate))){
						throw new DDPException("parse.date.error","日期格式错误");
					}
					
					return returnDate;
				}
				
				
				
			}catch (ParseException e) {
				e.printStackTrace();
				throw new DDPException("parse.date.error","日期格式错误");
			}
		}

		return null;
	}
	
	/**
	 * Excel导出操作
	 * @param modelName 导出模板名
	 * @param beans 导出数据
	 * @param response HttpServletResponse
	 * @return 导出结果
	 */
	public static DodopalResponse<String> excelExport(String modelName,Map<String, Object> beans, HttpServletResponse response) {
		InputStream istream = null;
		DodopalResponse<String> rep= new DodopalResponse<String>();
		rep.setCode(ResponseCode.SUCCESS);
		try {

			// 数据
//			List<HashMap<String, Object>> results = (List<HashMap<String, Object>>) beans.get("results");
//			// sheet名称
//			List<String> sheetNames = (List<String>) beans.get("sheetNames");
			Configuration config = new Configuration();
			XLSTransformer transformer = new XLSTransformer(config);
			istream = new BufferedInputStream(new FileInputStream(FILEPATH + FILE_SEPARATOR + modelName + ".xlsx"));
			// 创建一个sheet
			Workbook workbook = transformer.transformXLS(istream, beans);
			// 创建多个sheet
			//当前时间戳
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			//Workbook workbook = transformer.transformMultipleSheetsList(istream, results, sheetNames, "head" , new HashMap<Object, Object>(), 0);
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + modelName +df.format(new Date())+".xlsx\"");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");	// for js callback
			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			istream.close();
			return rep;
		} catch (Exception e) {
			rep.setCode(ResponseCode.UNKNOWN_ERROR);
			e.printStackTrace();
			if (istream != null) {
				try {
					istream.close();
				} catch (IOException ex) {
					
				}
			}
			return rep;
		}
	}

	/**
	 * Excel导出，具体例子可参考OSS项目ProductOrderServiceImpl.excelExport方法
	 * @param excelModel
	 * @param response
	 * @return
	 */
	@SuppressWarnings("resource")
	public static DodopalResponse<String> excelExport(ExcelModel excelModel, HttpServletResponse response) {
		DodopalResponse<String> reponse = new DodopalResponse<String>();
		InputStream inputStrean = null;
		try {
			// 数据集合
			List<List<String>> dataList = excelModel.getDataList();

			int resultSize = excelModel.getRowCount();

			// 导出最大记录数
			int exportMaxNum = excelModel.getExportMaxNum();
			if (exportMaxNum > 0 && resultSize > exportMaxNum) {
				// 不能超出最大导出数
				logger.warn("export over max size ：" + exportMaxNum + ", real size : " + resultSize);
				reponse.setCode(ResponseCode.EXCEL_EXPORT_OVER_MAX);
				return reponse;
			}

			// 模板名称,如果指定模板名称,则直接将数据集合写入指定的模板文件;否则使用通用模板commonTemplate
			String templateName = excelModel.getTemplate();
			// 是否使用通用模板
			boolean useCommonTemplate = true;
			if (StringUtils.isNotBlank(templateName)) {
				useCommonTemplate = false;
			} else {
				templateName = DEFAULT_TEMPLATE_NAME;
			}

			// 导出文件名
			String fileName = excelModel.getFileName();
			if (useCommonTemplate) {
				if (StringUtils.isBlank(fileName)) {
					fileName = DEFAULT_TEMPLATE_NAME;
				}
			} else {
				fileName = templateName;
			}

			// 标题
			String title = excelModel.getTitle();
			// 列标题
			List<String> titleList = excelModel.getTitleList();
			int columnNum = excelModel.getColumnCount();

			// sheet名称,不传入则使用默认名称
			String sheetName = excelModel.getSheetName();
			if (StringUtils.isBlank(sheetName)) {
				sheetName = DEFAULT_SHEET_NAME;
			}
			// 数据行开始位置
			Integer dataStartIndex = excelModel.getDataStartIndex();
			if (useCommonTemplate || dataStartIndex == null || dataStartIndex < 0) {
				dataStartIndex = DEFAULT_DATA_START_INDEX;
			}

			// 记录导出时间
			long t1 = System.currentTimeMillis();

			String filePahtName = FILEPATH + File.separator + templateName + FILE_SUFFIX;

			// 校验模板文件是否存在
			File checkFile = new File(filePahtName);
			if (!checkFile.exists()) {
				logger.warn("export template not found : " + filePahtName);
				reponse.setCode(ResponseCode.EXCEL_TEMPLATE_NOT_FOUND);
				return reponse;
			}

			inputStrean = new BufferedInputStream(new FileInputStream(filePahtName));
			// 读取excel
			XSSFWorkbook readbook = new XSSFWorkbook(inputStrean);

			// 通用模板标题样式
			CellStyle templateTitleStyle = null;
			// 通用模板列标题样式
			CellStyle templateTitleCellStyle = null;
			// 通用模板数据行样式
			CellStyle templateDataCellStyle = null;
			if (useCommonTemplate) {
				// 获取第一个sheet用来获取模板样式
				Sheet template = readbook.getSheetAt(0);
				// 模板标题样式
				Row templateTitleRow = template.getRow(0);
				Cell templateTitleCell = templateTitleRow.getCell(0);
				templateTitleStyle = templateTitleCell.getCellStyle();
				// 模板列标题样式
				Row templateColumnTitleRow = template.getRow(2);
				Cell templateColumnTitleCell = templateColumnTitleRow.getCell(0);
				templateTitleCellStyle = templateColumnTitleCell.getCellStyle();
				// 数据行样式
				Row templateDataRow = template.getRow(4);
				Cell templateDataCell = templateDataRow.getCell(0);
				templateDataCellStyle = templateDataCell.getCellStyle();
			}

			// 写入excel
			SXSSFWorkbook writebook = new SXSSFWorkbook(readbook, 1000);
			// 创建数据sheet
			Sheet sheet = null;
			if (useCommonTemplate) {
				sheet = writebook.createSheet(sheetName);
				// 整体设置列宽
				sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);

				// 创建标题行，并进行设置
				Row titleRow = sheet.createRow(DEFAULT_TITLE_NUM);
				titleRow.setHeightInPoints(30);
				for (int i = 0; i < columnNum; i++) {
					Cell tempCell = titleRow.createCell(i);
					tempCell.setCellStyle(templateTitleStyle);
				}
				// 设置标题名称
				Cell titleCell = titleRow.getCell(0);
				titleCell.setCellValue(title);
				// 合并标题行
				CellRangeAddress region = new CellRangeAddress(0, 0, 0, columnNum - 1);
				sheet.addMergedRegion(region);

				// 创建列标题行，并进行设置
				if (columnNum > 0) {
					Row columnTitleRow = sheet.createRow(DEFAULT_COLUMN_TITLE_NUM);
					columnTitleRow.setHeightInPoints(25);
					for (int i = 0; i < columnNum; i++) {
						Cell tempCell = columnTitleRow.createCell(i);
						tempCell.setCellValue(titleList.get(i));
						tempCell.setCellStyle(templateTitleCellStyle);
					}
				}
			} else {
				sheet = writebook.getSheetAt(0);
			}

			// 写入数据集合
			if (resultSize > 0) {
				int iData = 0;
				for (int rownum = dataStartIndex; rownum < resultSize + dataStartIndex; rownum++) {
					Row dataRow = sheet.createRow(rownum);
					dataRow.setHeightInPoints(DEFAULT_DATA_ROW_HEIGHT);
					List<String> rowList = dataList.get(iData++);
					if (useCommonTemplate) {
						if (rowList.size() < columnNum) {
							throw new DDPException(ResponseCode.SYSTEM_ERROR);
						}
					} else {
						columnNum = rowList.size();
					}
					int jData = 0;
					for (int cellnum = 0; cellnum < columnNum; cellnum++) {
						Cell cell = dataRow.createCell(cellnum);
						String value = rowList.get(jData++);
						if (value == null) {
							value = "";
						}
						cell.setCellValue(value);
						cell.setCellStyle(templateDataCellStyle);
					}
				}
			}

			// 删除模板sheet
			if (useCommonTemplate) {
				readbook.removeSheetAt(0);
			}

			response.setContentType("application/x-msdownload");
			// 给文件加上时间后缀
			String dateSuffix = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR);
			String exportFileName = fileName + "_" + dateSuffix + FILE_SUFFIX;
			response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "\"");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");	// for js callback
			ServletOutputStream out = response.getOutputStream();
			writebook.write(out);
			out.flush();
			out.close();

			if (logger.isInfoEnabled()) {
				logger.info("Excel导出成功,导出文件名:" + exportFileName + ", 导出耗时:" + (System.currentTimeMillis() - t1) + " ms");
			}

			reponse.setCode(ResponseCode.SUCCESS);
		} catch (Exception e) {
			reponse.setCode(ResponseCode.SYSTEM_ERROR);
			if (logger.isErrorEnabled()) {
				logger.error("Excel导出异常", e);
			}
		} finally {
			if (inputStrean != null) {
				try {
					inputStrean.close();
				} catch (IOException ex) {
					if (logger.isErrorEnabled()) {
						logger.error(ex.getMessage(), ex);
					}
				}
			}
		}
		return reponse;
	}

	/**
	 * @param wb
	 * @param list
	 * @param excelHeader
	 * @param index
	 * @param sheetName
	 * @return String 状态码
	 */
	public static <T> String excelExport(SXSSFWorkbook wb, List<T> list, String[] excelHeader, String[] index, String sheetName) {
		String msgCode = new String();
		
		Sheet sheet = wb.createSheet(sheetName);
		// 整体设置列宽
		sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
		sheet.setDefaultRowHeightInPoints(DEFAULT_DATA_ROW_HEIGHT);

		// 创建  标题  行，并进行设置
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(30);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(getTitleCellStyle(wb));
		titleCell.setCellValue(sheetName);
		// 合并标题行
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, index.length - 1);
		sheet.addMergedRegion(region);
		
		// 创建  列标题  行，并进行设置
		Row titleColRow = sheet.createRow(2);
		titleColRow.setHeightInPoints(25);
		CellStyle titleColStyle = getTitleColStyle(wb);
		for(int i = 0; i < excelHeader.length; i++) {
			Cell cell = titleColRow.createCell(i);
			cell.setCellStyle(titleColStyle);
			cell.setCellValue(excelHeader[i]);
		}
		
		// 写数据
		Row dataRow;
		CellStyle dataCellStyle = getDataCellStyle(wb);
		for(int listIndex = 0; listIndex < list.size(); listIndex++) {
			dataRow = sheet.createRow(listIndex + 3);
			T t = list.get(listIndex);
			for(int j = 0; j < index.length; j++) {
				String methodName = "get"+index[j].substring(0, 1).toUpperCase()+index[j].substring(1);
				try {
					Cell dataCell = dataRow.createCell(j);
					dataCell.setCellStyle(dataCellStyle);
					
					Method method = t.getClass().getMethod(methodName);
					Object value = method.invoke(t);
		   			if(null != value) {
		   				String valClass = value.getClass().toString();
		   				String claStr = valClass.substring(valClass.lastIndexOf(".")+1);
		   				if(claStr.equals("Date")) {
		   					dataCell.setCellValue(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(value));
		   				}else {
		   					dataCell.setCellValue(value.toString());
		   				}
		   			}else {
		   				dataCell.setCellValue("");
		   			}
				} catch (NoSuchMethodException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射没有这样的方法.");
					e.printStackTrace();
				} catch (SecurityException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: .");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				}
			}
			msgCode = new String(ResponseCode.SUCCESS);
		}
		return msgCode;
	}
	
	private static CellStyle getTitleCellStyle(SXSSFWorkbook wb) {
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		titleFont.setFontName("宋体");
		CellStyle titleCellStyle = wb.createCellStyle();
		titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleCellStyle.setFont(titleFont);
		return titleCellStyle;
	};
	private static CellStyle getTitleColStyle(SXSSFWorkbook wb) {
		Font titleColFont = wb.createFont();
		titleColFont.setFontName("宋体");
		titleColFont.setFontHeightInPoints((short) 10);
		titleColFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle titleColStyle = wb.createCellStyle();
		titleColStyle.setFillForegroundColor((short) 44);
		titleColStyle.setBorderTop(CellStyle.BORDER_THIN);
		titleColStyle.setBorderBottom(CellStyle.BORDER_THIN);
		titleColStyle.setBorderRight(CellStyle.BORDER_THIN);
		titleColStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleColStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleColStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleColStyle.setFont(titleColFont);
		return titleColStyle;
	}
	private static CellStyle getDataCellStyle(SXSSFWorkbook wb) {
		Font dataFont = wb.createFont();
		dataFont.setFontName("宋体");
		dataFont.setFontHeightInPoints((short) 11);
		CellStyle dataStyle = wb.createCellStyle();
		dataStyle.setBorderRight(CellStyle.BORDER_THIN);
		dataStyle.setBorderBottom(CellStyle.BORDER_THIN);
		dataStyle.setFont(dataFont);
		return dataStyle;
	}
	
	/**
	 * @author Mikaelyan
	 * @param resp 响应
	 * @param list 结果集
	 * @param index	列索引和列名有序集
	 * @param sheetName 文件名
	 * @return 返回识别码 执行正常和识别码为"000000"
	 */
	public static <T> String excelExport(HttpServletRequest req, HttpServletResponse resp, List<T> list, Map<String, String> index, String sheetName,Map<String, String> titleMap) {
		String msgCode = new String();
		
		String[] key = new String[index.size()];
		String[] val = new String[index.size()];
		int tempI = 0;
		for(String k : index.keySet()) {
			key[tempI] = k;
			if(titleMap.get(k) != null){
				val[tempI] = titleMap.get(k);
			}else{
				val[tempI] = index.get(k);
			}
			tempI = tempI + 1;
		}
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		// 整体设置列宽
		sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
		sheet.setDefaultRowHeightInPoints(DEFAULT_DATA_ROW_HEIGHT);

		// 创建  标题  行，并进行设置
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(30);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(getTitleCellStyle(wb));
		titleCell.setCellValue(sheetName);
		// 合并标题行
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, index.size() - 1);
		sheet.addMergedRegion(region);
		
		// 创建  列标题  行，并进行设置
		Row titleColRow = sheet.createRow(2);
		titleColRow.setHeightInPoints(25);
		CellStyle titleColStyle = getTitleColStyle(wb);
		for(int i = 0; i < val.length; i++) {
			Cell cell = titleColRow.createCell(i);
			cell.setCellStyle(titleColStyle);
			cell.setCellValue(val[i]);
		}
		
		// 写数据
		Row dataRow;
		CellStyle dataCellStyle = getDataCellStyle(wb);
		for(int listIndex = 0; listIndex < list.size(); listIndex++) {
			dataRow = sheet.createRow(listIndex + 3);
			T t = list.get(listIndex);
			for(int j = 0; j < key.length; j++) {
				String methodName = "get"+key[j].substring(0, 1).toUpperCase()+key[j].substring(1);
				try {
					Cell dataCell = dataRow.createCell(j);
					dataCell.setCellStyle(dataCellStyle);
					
					Method method = t.getClass().getMethod(methodName);
					Object value = method.invoke(t);
		   			if(null != value) {
		   				String valClass = value.getClass().toString();
		   				String claStr = valClass.substring(valClass.lastIndexOf(".")+1);
		   				if(claStr.equals("Date")) {
		   					dataCell.setCellValue(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(value));
		   				}else {
		   					dataCell.setCellValue(value.toString());
		   				}
		   			}else {
		   				dataCell.setCellValue("");
		   			}
				} catch (NoSuchMethodException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射没有这样的方法.");
					e.printStackTrace();
				} catch (SecurityException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 安全错误.");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 非法访问.");
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				}
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		String nameStr;
		try {
			String agent = req.getHeader("User-Agent");
			boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
			if (isMSIE) {
				nameStr = URLEncoder.encode((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx"), "UTF-8");
			}else {
				nameStr = new String((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx").getBytes("UTF-8"), "ISO-8859-1");
			}
			resp.setHeader("Content-Disposition", "attachment;filename=" + nameStr);
			resp.setHeader("Set-Cookie", "fileDownload=true; path=/");	// for js callback
			OutputStream ouputStream = resp.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			msgCode = new String(ResponseCode.SUCCESS);
		} catch (UnsupportedEncodingException e) {
			msgCode = new String(ResponseCode.SYSTEM_ERROR);
			logger.error("Excel导出错误: 文件名转码错误.");
			e.printStackTrace();
		} catch (IOException e) {
			msgCode = new String(ResponseCode.SYSTEM_ERROR);
			logger.error("Excel导出错误: 流错误.");
			e.printStackTrace();
		}
		
		return msgCode;
	}
	
	
	public static <T> String excelExport(HttpServletRequest req, HttpServletResponse resp, List<T> list, Map<String, String> index, String sheetName) {
		String msgCode = new String();
		
		String[] key = new String[index.size()];
		String[] val = new String[index.size()];
		int tempI = 0;
		for(String k : index.keySet()) {
			key[tempI] = k;
			val[tempI] = index.get(k);
			tempI = tempI + 1;
		}
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		// 整体设置列宽
		sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
		sheet.setDefaultRowHeightInPoints(DEFAULT_DATA_ROW_HEIGHT);

		// 创建  标题  行，并进行设置
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(30);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(getTitleCellStyle(wb));
		titleCell.setCellValue(sheetName);
		// 合并标题行
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, index.size() - 1);
		sheet.addMergedRegion(region);
		
		// 创建  列标题  行，并进行设置
		Row titleColRow = sheet.createRow(2);
		titleColRow.setHeightInPoints(25);
		CellStyle titleColStyle = getTitleColStyle(wb);
		for(int i = 0; i < val.length; i++) {
			Cell cell = titleColRow.createCell(i);
			cell.setCellStyle(titleColStyle);
			cell.setCellValue(val[i]);
		}
		
		// 写数据
		Row dataRow;
		CellStyle dataCellStyle = getDataCellStyle(wb);
		for(int listIndex = 0; listIndex < list.size(); listIndex++) {
			dataRow = sheet.createRow(listIndex + 3);
			T t = list.get(listIndex);
			for(int j = 0; j < key.length; j++) {
				String methodName = "get"+key[j].substring(0, 1).toUpperCase()+key[j].substring(1);
				try {
					Cell dataCell = dataRow.createCell(j);
					dataCell.setCellStyle(dataCellStyle);
					
					Method method = t.getClass().getMethod(methodName);
					Object value = method.invoke(t);
		   			if(null != value) {
		   				String valClass = value.getClass().toString();
		   				String claStr = valClass.substring(valClass.lastIndexOf(".")+1);
		   				if(claStr.equals("Date")) {
		   					dataCell.setCellValue(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(value));
		   				}else {
		   					dataCell.setCellValue(value.toString());
		   				}
		   			}else {
		   				dataCell.setCellValue("");
		   			}
				} catch (NoSuchMethodException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射没有这样的方法.");
					e.printStackTrace();
				} catch (SecurityException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 安全错误.");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 非法访问.");
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					msgCode = new String(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				}
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		String nameStr;
		try {
			String agent = req.getHeader("User-Agent");
			boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
			if (isMSIE) {
				nameStr = URLEncoder.encode((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx"), "UTF-8");
			}else {
				nameStr = new String((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx").getBytes("UTF-8"), "ISO-8859-1");
			}
			resp.setHeader("Content-Disposition", "attachment;filename=" + nameStr);
			resp.setHeader("Set-Cookie", "fileDownload=true; path=/");	// for js callback
			OutputStream ouputStream = resp.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			msgCode = new String(ResponseCode.SUCCESS);
		} catch (UnsupportedEncodingException e) {
			msgCode = new String(ResponseCode.SYSTEM_ERROR);
			logger.error("Excel导出错误: 文件名转码错误.");
			e.printStackTrace();
		} catch (IOException e) {
			msgCode = new String(ResponseCode.SYSTEM_ERROR);
			logger.error("Excel导出错误: 流错误.");
			e.printStackTrace();
		}
		
		return msgCode;
	}
	
	public static <T, V> String excelExportLastLine(HttpServletRequest req, HttpServletResponse resp, List<T> list, Map<String, String> index, String sheetName,List <V> lastLine) {
        String msgCode = new String();
        
        String[] key = new String[index.size()];
        String[] val = new String[index.size()];
        int tempI = 0;
        for(String k : index.keySet()) {
            key[tempI] = k;
            val[tempI] = index.get(k);
            tempI = tempI + 1;
        }
        
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Sheet sheet = wb.createSheet(sheetName);
        // 整体设置列宽
        sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
        sheet.setDefaultRowHeightInPoints(DEFAULT_DATA_ROW_HEIGHT);

        // 创建  标题  行，并进行设置
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(30);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(getTitleCellStyle(wb));
        titleCell.setCellValue(sheetName);
        // 合并标题行
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, index.size() - 1);
        sheet.addMergedRegion(region);
        
        // 创建  列标题  行，并进行设置
        Row titleColRow = sheet.createRow(2);
        titleColRow.setHeightInPoints(25);
        CellStyle titleColStyle = getTitleColStyle(wb);
        for(int i = 0; i < val.length; i++) {
            Cell cell = titleColRow.createCell(i);
            cell.setCellStyle(titleColStyle);
            cell.setCellValue(val[i]);
        }
        
        // 写数据
        Row dataRow;
        CellStyle dataCellStyle = getDataCellStyle(wb);
        for(int listIndex = 0; listIndex < list.size(); listIndex++) {
            dataRow = sheet.createRow(listIndex + 3);
            T t = list.get(listIndex);
            for(int j = 0; j < key.length; j++) {
                String methodName = "get"+key[j].substring(0, 1).toUpperCase()+key[j].substring(1);
                try {
                    Cell dataCell = dataRow.createCell(j);
                    dataCell.setCellStyle(dataCellStyle);
                    
                    Method method = t.getClass().getMethod(methodName);
                    Object value = method.invoke(t);
                    if(null != value) {
                        String valClass = value.getClass().toString();
                        String claStr = valClass.substring(valClass.lastIndexOf(".")+1);
                        if(claStr.equals("Date")) {
                            dataCell.setCellValue(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(value));
                        }else {
                            dataCell.setCellValue(value.toString());
                        }
                    }else {
                        dataCell.setCellValue("");
                    }
                } catch (NoSuchMethodException e) {
                    msgCode = new String(ResponseCode.SYSTEM_ERROR);
                    logger.error("Excel导出错误: 反射没有这样的方法.");
                    e.printStackTrace();
                } catch (SecurityException e) {
                    msgCode = new String(ResponseCode.SYSTEM_ERROR);
                    logger.error("Excel导出错误: 安全错误.");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    msgCode = new String(ResponseCode.SYSTEM_ERROR);
                    logger.error("Excel导出错误: 非法访问.");
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    msgCode = new String(ResponseCode.SYSTEM_ERROR);
                    logger.error("Excel导出错误: 反射调方法入参非法错误.");
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    msgCode = new String(ResponseCode.SYSTEM_ERROR);
                    logger.error("Excel导出错误: 反射调方法入参非法错误.");
                    e.printStackTrace();
                }
            }
        }
        //在数据填装完毕后，构造表格下方的提示信息
        Row lastRow;
        if(CollectionUtils.isNotEmpty(lastLine)){
            for(V v:lastLine){
                int i=0;
                int lastLineNum = list.size()+3 + i++;
                lastRow = sheet.createRow(lastLineNum);
                if(v instanceof Map){
                    //TODO  报表导出 谁用谁加 泛型为map时的处理 
                }else if(v instanceof String){
                    Cell lastCell = lastRow.createCell(0);
                    lastCell.setCellValue(v.toString());
                    lastCell.setCellStyle(dataCellStyle);
                    CellRangeAddress lastTempLine = new CellRangeAddress(lastLineNum, lastLineNum, 0, index.size() - 1);
                    sheet.addMergedRegion(lastTempLine); 
                }
            }
        }
       
       // list.size()+3
        
        resp.setContentType("application/vnd.ms-excel");
        String nameStr;
        try {
            String agent = req.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            if (isMSIE) {
                nameStr = URLEncoder.encode((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx"), "UTF-8");
            }else {
                nameStr = new String((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx").getBytes("UTF-8"), "ISO-8859-1");
            }
            resp.setHeader("Content-Disposition", "attachment;filename=" + nameStr);
            resp.setHeader("Set-Cookie", "fileDownload=true; path=/");  // for js callback
            OutputStream ouputStream = resp.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
            msgCode = new String(ResponseCode.SUCCESS);
        } catch (UnsupportedEncodingException e) {
            msgCode = new String(ResponseCode.SYSTEM_ERROR);
            logger.error("Excel导出错误: 文件名转码错误.");
            e.printStackTrace();
        } catch (IOException e) {
            msgCode = new String(ResponseCode.SYSTEM_ERROR);
            logger.error("Excel导出错误: 流错误.");
            e.printStackTrace();
        }
        
        return msgCode;
    }
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @param list 
	 * @param index
	 * @param sheetName
	 * @return DodopalResponse<String>
	 */
	public static <T> DodopalResponse<String> ddpExcelExport(HttpServletRequest req, HttpServletResponse resp, DodopalResponse<DodopalDataPage<T>> ddRes, Map<String, String> index, String sheetName) {
		DodopalResponse<String> reponse = new DodopalResponse<String>();
//		String msgCode = new String();
		
		String[] key = new String[index.size()];
		String[] val = new String[index.size()];
		int tempI = 0;
		for(String k : index.keySet()) {
			key[tempI] = k;
			val[tempI] = index.get(k);
			tempI = tempI + 1;
		}
		
		SXSSFWorkbook wb = new SXSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		// 整体设置列宽
		sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
		sheet.setDefaultRowHeightInPoints(DEFAULT_DATA_ROW_HEIGHT);

		// 创建  标题  行，并进行设置
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(30);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(getTitleCellStyle(wb));
		titleCell.setCellValue(sheetName);
		// 合并标题行
		CellRangeAddress region = new CellRangeAddress(0, 0, 0, index.size() - 1);
		sheet.addMergedRegion(region);
		
		// 创建  列标题  行，并进行设置
		Row titleColRow = sheet.createRow(2);
		titleColRow.setHeightInPoints(25);
		CellStyle titleColStyle = getTitleColStyle(wb);
		for(int i = 0; i < val.length; i++) {
			Cell cell = titleColRow.createCell(i);
			cell.setCellStyle(titleColStyle);
			cell.setCellValue(val[i]);
		}
		
		List<T> list = ddRes.getResponseEntity().getRecords();
		
		// 写数据
		Row dataRow;
		CellStyle dataCellStyle = getDataCellStyle(wb);
		for(int listIndex = 0; listIndex < list.size(); listIndex++) {
			dataRow = sheet.createRow(listIndex + 3);
			T t = list.get(listIndex);
			for(int j = 0; j < key.length; j++) {
				String methodName = "get"+key[j].substring(0, 1).toUpperCase()+key[j].substring(1);
				try {
					Cell dataCell = dataRow.createCell(j);
					dataCell.setCellStyle(dataCellStyle);
					
					Method method = t.getClass().getMethod(methodName);
					Object value = method.invoke(t);
		   			if(null != value) {
		   				String valClass = value.getClass().toString();
		   				String claStr = valClass.substring(valClass.lastIndexOf(".")+1);
		   				if(claStr.equals("Date")) {
		   					dataCell.setCellValue(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(value));
		   				}else {
		   					dataCell.setCellValue(value.toString());
		   				}
		   			}else {
		   				dataCell.setCellValue("");
		   			}
				} catch (NoSuchMethodException e) {
					reponse.setCode(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射没有这样的方法.");
					e.printStackTrace();
				} catch (SecurityException e) {
					reponse.setCode(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 安全错误.");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					reponse.setCode(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 非法访问.");
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					reponse.setCode(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					reponse.setCode(ResponseCode.SYSTEM_ERROR);
					logger.error("Excel导出错误: 反射调方法入参非法错误.");
					e.printStackTrace();
				}
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		String nameStr;
		try {
			String agent = req.getHeader("User-Agent");
			boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
			if (isMSIE) {
				nameStr = URLEncoder.encode((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx"), "UTF-8");
			}else {
				nameStr = new String((sheetName+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx").getBytes("UTF-8"), "ISO-8859-1");
			}
			resp.setHeader("Content-Disposition", "attachment;filename=" + nameStr);
			resp.setHeader("Set-Cookie", "fileDownload=true; path=/");	// for js callback
			OutputStream ouputStream = resp.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			reponse.setCode(ResponseCode.SUCCESS);
		} catch (UnsupportedEncodingException e) {
			reponse.setCode(ResponseCode.SYSTEM_ERROR);
			logger.error("Excel导出错误: 文件名转码错误.");
			e.printStackTrace();
		} catch (IOException e) {
			reponse.setCode(ResponseCode.SYSTEM_ERROR);
			logger.error("Excel导出错误: 流错误.");
			e.printStackTrace();
		}
		
		return reponse;
	}
	
}

	
