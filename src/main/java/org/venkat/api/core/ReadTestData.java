package org.venkat.api.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.venkat.api.pojo.ConfigAPI;
import org.venkat.api.pojo.TestCasesData;
import org.venkat.api.pojo.TestData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;



public class ReadTestData {

	private XSSFWorkbook workBook;
//	private static Map<String, Map<String, String>> testDataMap = new HashMap<String, Map<String, String>>();
	private static Map<String, TestData> testDataMap1 = new HashMap<String, TestData>();
	private static Map<String, String> xpathDataMap = new HashMap<String, String>();
	private static Map<String, TestCasesData> testCasesDataMap = new HashMap<String, TestCasesData>();
	public static GlobalConstants fv;

	public ReadTestData() {
		try {
			workBook = new XSSFWorkbook(new FileInputStream(GlobalConstants.testDataFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ConfigAPI getConfigAPIObject() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		ConfigAPI configAPI = null;
        try {
        	configAPI = mapper.readValue(new File(GlobalConstants.configAPIFilePath), ConfigAPI.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return configAPI;
	}
	
	
	private XSSFSheet getsheet(String sheetname) {
		XSSFSheet Sheet = workBook.getSheet(sheetname);
		return Sheet;
	}
	
	private static String getCellData(Cell cell) {
		String data = "";
		if (cell == null)
			return data;
		else {
			@SuppressWarnings("deprecation")
			CellType cellType = cell.getCellTypeEnum();
			switch (cellType) {
			case STRING:
				data = cell.getStringCellValue().trim();
				break;
			case NUMERIC:
				data = Double.toString(cell.getNumericCellValue()).trim();
				break;
			case BOOLEAN:
				data = Boolean.toString(cell.getBooleanCellValue()).trim();
				break;
			case BLANK:
				break;
			default:
				System.out.println("Unexpected cell type....!");
				break;
			}
			return data;
		}
	}
	
	private static String getCellData(Row row, int index) {
		return getCellData(row.getCell(index));
	}
	
	private static int getColeamIndex(Row row, String colName) {
		int count = -1;
		Iterator<Cell> cells = row.cellIterator();
		while (cells.hasNext()) {
			Cell cell = (Cell) cells.next();
			count++;
			if (colName.equalsIgnoreCase(getCellData(cell)))
				return count;
		}
		return -1;
	}

	/*public Map<String, Map<String, String>> getTestData() {
		XSSFSheet tcSheet = getsheet("testData");
		Iterator<Row> rows = tcSheet.iterator();
		// to store header row
		Row hRow = rows.next();
		while (rows.hasNext()) {
			Map<String, String> rowMap = new HashMap<String, String>();
			Row row = rows.next();
			for (int cellIndex = 1; cellIndex <= row.getLastCellNum(); cellIndex++)
				rowMap.put(getCellData(hRow, cellIndex), getCellData(row, cellIndex));
			testDataMap.put(getCellData(row, 0), rowMap);
		}
		return testDataMap;
	}*/

	public Map<String, TestData> getTestData() {
		XSSFSheet tcSheet = getsheet("testData");
		Iterator<Row> rows = tcSheet.iterator();
		// to store header row
		Row hRow = rows.next();
		int tcIDIndex = getColeamIndex(hRow, "TC_ID");
		int fName = getColeamIndex(hRow, "fName");
		int lName = getColeamIndex(hRow, "lName");
		int uName = getColeamIndex(hRow, "uName");
		int password = getColeamIndex(hRow, "password");
		int customor = getColeamIndex(hRow, "customor");
		int role = getColeamIndex(hRow, "role");
		int email = getColeamIndex(hRow, "email");
		int cell = getColeamIndex(hRow, "cell");
		while (rows.hasNext()) {
			TestData testData = new TestData();
			Row row = rows.next();
			testData.setTc_id(getCellData(row, tcIDIndex));
			testData.setfName(getCellData(row, fName));
			testData.setlName(getCellData(row, lName));
			testData.setuName(getCellData(row, uName));
			testData.setPassword(getCellData(row, password));
			testData.setCustomor(getCellData(row, customor));
			testData.setRole(getCellData(row, role));
			testData.setEmail(getCellData(row, email));
			testData.setCell(getCellData(row, cell));
			testDataMap1.put(getCellData(row, tcIDIndex), testData);
		}
		return testDataMap1;
	}

	public Map<String, TestCasesData> getTestCaseData() {
		XSSFSheet tcSheet = getsheet("testCase");
		Iterator<Row> rows = tcSheet.iterator();
		// to store header row
		Row hRow = rows.next();
		int testCasesNameIndex = getColeamIndex(hRow, "Test_Cases_Name");
		int executionFlagIndex = getColeamIndex(hRow, "Execution_Flag");
		int classNameIndex = getColeamIndex(hRow, "Class_Name");
		while (rows.hasNext()) {
			TestCasesData testCasesData = new TestCasesData();
			Row row = rows.next();
			testCasesData.setTestCasesName(getCellData(row, testCasesNameIndex));
			testCasesData.setExecutionFlag(getCellData(row, executionFlagIndex));
			testCasesData.setClassName(getCellData(row, classNameIndex));
			testCasesDataMap.put(testCasesData.getTestCasesName(), testCasesData);
		}
		return testCasesDataMap;
	}

	public Map<String, String> getXpathData() {
		XSSFSheet tcSheet = getsheet("xpathData");
		Iterator<Row> rows = tcSheet.iterator();
		// to store header row
		Row hRow = rows.next();
		int keyIndex = getColeamIndex(hRow, "Key");
		int valueIndex;
		if ("English".equalsIgnoreCase(GlobalConstants.locale))
			valueIndex = getColeamIndex(hRow, "English");
		else if("Spanish".equalsIgnoreCase(GlobalConstants.locale))
			valueIndex = getColeamIndex(hRow, "Spanish");
		else
			valueIndex = getColeamIndex(hRow, "Arabic");
		while (rows.hasNext()) {
			Row row = rows.next();
			xpathDataMap.put(getCellData(row, keyIndex), getCellData(row, valueIndex));
		}
		return xpathDataMap;
	}

}