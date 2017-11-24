package com.bentengwu.utillib.String;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* StrUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 19, 2017</pre> 
* @version 1.0 
*/ 
public class StrUtilsTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: isEmpty(String str) 
* 
*/ 
@Test
public void testIsEmpty() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isNotEmpty(String str) 
* 
*/ 
@Test
public void testIsNotEmpty() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: arrayToString(String[] paramArrayOfString) 
* 
*/ 
@Test
public void testArrayToStringParamArrayOfString() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: arrayToString(String[] paramArrayOfString, String paramString) 
* 
*/ 
@Test
public void testArrayToStringForParamArrayOfStringParamString() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isDigitalChar(String strNumber) 
* 
*/ 
@Test
public void testIsDigitalChar() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getString(Object strValue) 
* 
*/ 
@Test
public void testGetStringStrValue() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getString(Object strValue, String replaceIfNull) 
* 
*/ 
@Test
public void testGetStringForStrValueReplaceIfNull() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: expandStr(String str, int len, char ch, boolean fillOnLeft) 
* 
*/ 
@Test
public void testExpandStr() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setEndswith(String str, String ch) 
* 
*/ 
@Test
public void testSetEndswith() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: trimStart(final String str, final String ch) 
* 
*/ 
@Test
public void testTrimStart() throws Exception {
    String test1 = "///asdf/asdf/asdf";
    String test2 = "/asdf/asdf/asdf";
    String test3 = null;

    String trim1 = "/";
    String trim2 = null;

    String tt1 =  StrUtils.trimStart(test1, trim1);
    String tt2 = StrUtils.trimStart(test2, trim1);
    String tt3 = StrUtils.trimStart(test3, trim1);
    String tt12 = StrUtils.trimStart(test1, trim2);

    Assert.assertEquals(tt1,"asdf/asdf/asdf");
    Assert.assertEquals(tt2,"asdf/asdf/asdf");
    Assert.assertNull(tt3);
    Assert.assertEquals(tt12, test1);
}

/** 
* 
* Method: replaceStr(String strSrc, String strOld, String strNew) 
* 
*/ 
@Test
public void testReplaceStrForStrSrcStrOldStrNew() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: replaceStrByIndex(String srcStr, String aimStr, int start, String...args) 
* 
*/ 
@Test
public void testReplaceStrByIndex() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: byteToString(byte bytes[], char ch, int radix) 
* 
*/ 
@Test
public void testByteToString() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getBytesUTF8(String data) 
* 
*/ 
@Test
public void testGetBytesUTF8() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getBytesLength(String str) 
* 
*/ 
@Test
public void testGetBytesLength() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: split(String str, String regx) 
* 
*/ 
@Test
public void testSplit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: transDisplay(String content) 
* 
*/ 
@Test
public void testTransDisplayContent() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: transDisplay(String content, boolean changeBlank) 
* 
*/ 
@Test
public void testTransDisplayForContentChangeBlank() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: containsIgnoreCase(String str1, String str2) 
* 
*/ 
@Test
public void testContainsIgnoreCase() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: hasFullChar(String str) 
* 
*/ 
@Test
public void testHasFullChar() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: array2List(String array[]) 
* 
*/ 
@Test
public void testArray2List() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: splitAndFilterString(String input) 
* 
*/ 
@Test
public void testSplitAndFilterString() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getStrbyarray(String[] array) 
* 
*/ 
@Test
public void testGetStrbyarrayArray() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getStrbyarray(String[] array, String split) 
* 
*/ 
@Test
public void testGetStrbyarrayForArraySplit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getStrByArray(Object[] array, String split) 
* 
*/ 
@Test
public void testGetStrByArray() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: appendChar$str(String str, char endwith) 
* 
*/ 
@Test
public void testAppendChar$str() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: arr2collect(String array[], String classtype) 
* 
*/ 
@Test
public void testArr2collect() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: repStr2Numberstr(String str) 
* 
*/ 
@Test
public void testRepStr2Numberstr() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: removeDuplicate(String tomail, String split) 
* 
*/ 
@Test
public void testRemoveDuplicate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getFirstCapitalLoacl(String arg) 
* 
*/ 
@Test
public void testGetFirstCapitalLoacl() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSumFromList(List<String> list) 
* 
*/ 
@Test
public void testGetSumFromList() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: toOperateCode(long typecode) 
* 
*/ 
@Test
public void testToOperateCode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getRandomNumLetters(int length) 
* 
*/ 
@Test
public void testGetRandomNumLetters() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getRandomNumber(int len) 
* 
*/ 
@Test
public void testGetRandomNumber() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getRandomPool(int min, int max, int n) 
* 
*/ 
@Test
public void testGetRandomPool() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: list2Str(List list) 
* 
*/ 
@Test
public void testList2StrList() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: list2Str(List list, String split) 
* 
*/ 
@Test
public void testList2StrForListSplit() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: formatNumbers(Double numb, int decimals) 
* 
*/ 
@Test
public void testFormatNumbers() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: toStringReflection(Object t) 
* 
*/ 
@Test
public void testToStringReflection() throws Exception { 
//TODO: Test goes here... 
} 


} 
