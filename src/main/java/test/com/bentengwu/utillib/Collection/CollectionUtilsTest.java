package test.com.bentengwu.utillib.Collection; 

import com.bentengwu.utillib.Collection.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
* CollectionUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 24, 2017</pre> 
* @version 1.0 
*/ 
public class CollectionUtilsTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: addAll(Collection collection, Object...args) 
* 
*/ 
@Test
public void testAddAll() throws Exception {
    Set<String> set = new HashSet<>();
    List<String> list = new ArrayList<>();
    CollectionUtils.addAll(set,new String[]{"a","b","a"});
    CollectionUtils.addAll(list,new String[]{"a","b","a"});
    Assert.assertEquals(set.size(), 2);
    Assert.assertEquals(list.size(), 3);
}

/** 
* 
* Method: addAllArgs(Collection collection, Object[] args) 
* 
*/ 
@Test
public void testAddAllArgs() throws Exception {
    Set<String> set = new HashSet<>();
    List<String> list = new ArrayList<>();
    CollectionUtils.addAllArgs(set, new String[]{"a", "b", "a"});
    CollectionUtils.addAllArgs(list, new String[]{"a", "b", "a"});
    Assert.assertEquals(set.size(), 2);
    Assert.assertEquals(list.size(),3);
}


} 
