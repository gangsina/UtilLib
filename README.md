# UtilLib

今天决定启一个feature: beta4-cutdirtycode 用来清除一些业务有关的代码,保证这个项目只提供工具类.不切入业务. 业务性关系性比较强的还是在原先beta3.x中维护.



```
beta3.0
   删除swing相关的操作的工具类
   增加CollectionUtils工具类. 里面其实只有一个方法. 而且和Collections重复的.
2.0.4
   新增 ExceptionUtils 
      exception2String(Exception ex)
      
      在StrUtils中新增toStringReflection(Object t)方法 ，这个方法对StringUtils中的ToStringBuilder方法做了修改，减少了类的package部分。。
2.0.2.1
新增  获取classpath所在路径。项目的classpath路径
新增 读取class所在的文件绝对路径

2.0.2
增加工具类 JTableUtil.
    增加自适应内容长度的方法。
    

1.6.6版本
   这个版本有多个人修改过,无法维护.从2.0版本开始维护.
2.0版本
   修改了ExcelUtils 的
      public static List<String[]> getDataFromSheet(File file,int sheetOrder,int startRowNum)方法.
   主要为给部门工具导入通讯录用.
2.0.1 2.0.2 版本
增加 ExcelUtils 下的方法。
/**
 * 从2.0.1开始的结束下标。很多情况下，我们都是给出模版，并知道它有多少个列。
 * 经常自动获取最后列的时候，会出现最后一列刚好为空的情况，无法获取到最后一列。或者
 * 是该行当中有空列的情况，导致获取的列数非法。
 *<br />
 *@date 2015-2-3 上午10:58:15
 *@author <a href="bentengwu@163.com">thender</a>
 *@param file   excel文件
 *@param sheetOrder sheet的下标
 *@param startRowNum 开始行
 *@param lastRowNum  结束列的下标
 *@return 该sheet下的所有数据以数组列表的形式返回。
 *@throws IOException
 *@since 2.0.1
 */
```



