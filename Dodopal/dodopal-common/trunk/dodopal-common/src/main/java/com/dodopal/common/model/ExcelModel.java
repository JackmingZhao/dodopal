package com.dodopal.common.model;

import java.util.List;

import com.dodopal.common.util.ExcelUtil;

/**
 * @author lifeng@dodopal.com
 */

public class ExcelModel {
	// ====================================使用指定模板参数
	// 指定template名称
	private String template;
	// 数据行开始位置(未指定则从第四行开始写入数据)
	private Integer dataStartIndex;

	// ====================================使用通用模板参数
	// 导出文件名,不支持中文,默认为commonTemplate
	private String fileName;
	// 标题
	private String title;
	// 列标题
	private List<String> titleList;
	// sheet名称(默认为sheet1)
	private String sheetName;

	// ====================================公共参数
	// 数据集合
	private List<List<String>> dataList;
	// 最大导出记录数，小于等于0则不限制导出数量(慎用)
	private int exportMaxNum = ExcelUtil.EXPORT_MAX_COUNT;

	public ExcelModel(String template) {
		setTemplate(template);
	}

	public ExcelModel(String fileName, String title) {
		setFileName(fileName);
		setTitle(title);
	}

	public ExcelModel(String fileName, String title, List<String> titleList) {
		setFileName(fileName);
		setTitle(title);
		setTitleList(titleList);
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Integer getDataStartIndex() {
		return dataStartIndex;
	}

	public void setDataStartIndex(Integer dataStartIndex) {
		this.dataStartIndex = dataStartIndex;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
		if (titleList != null) {
			columnCount = titleList.size();
		}
	}

	public List<List<String>> getDataList() {
		return dataList;
	}

	public void setDataList(List<List<String>> dataList) {
		this.dataList = dataList;
		if (dataList != null) {
			rowCount = dataList.size();
		}
	}

	// 列数(无需设置，与列标题集合数量一致)
	private int columnCount;
	// 行数（无需设置，与数据集合数量一致）
	private int rowCount;

	public int getColumnCount() {
		return columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getExportMaxNum() {
		return exportMaxNum;
	}

	public void setExportMaxNum(int exportMaxNum) {
		this.exportMaxNum = exportMaxNum;
	}

}
