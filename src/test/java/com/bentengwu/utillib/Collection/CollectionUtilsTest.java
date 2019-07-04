package com.bentengwu.utillib.Collection;

import com.bentengwu.utillib.String.StrUtils;
import com.bentengwu.utillib.reflection.UtilReflection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import sun.util.calendar.Era;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** 
* CollectionUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>六月 28, 2019</pre> 
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
//TODO: Test goes here... 
} 

/** 
* 
* Method: addAllArgs(Collection collection, Object[] args) 
* 
*/ 
@Test
public void testAddAllArgs() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: newArray(T _defaultVal, int length) 
* 
*/ 
@Test
public void testNewArray() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: copy(final T[] fromArray, int fromArrayStartIndex, final T[] toArray, int toArrayStartIndex, int len) 
* 
*/ 
@Test
public void testCopy() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: clearEmptyElement(final Collection collection) 
* 
*/ 
@Test
public void testClearEmptyElement() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: clearFieldEmptyElement(final Collection collection, String fields) 
* 
*/ 
@Test
public void testClearFieldEmptyElement() throws Exception { 
//TODO: Test goes here...
    Eras entity = new Eras();
    entity.setStart_date("aaa");
    entity.setEnd_date("bbb");

    Set<Eras> set = new HashSet<>();
    set.add(entity);

    entity = new Eras();
    entity.setStart_date("aaa");
    entity.setEnd_date("");
    set.add(entity);

    entity = new Eras();
    entity.setStart_date("");
    entity.setEnd_date("bb");
    set.add(entity);

    int size =  CollectionUtils.clearFieldEmptyElement(set,"start_date,end_date");
    System.out.println(111);
    System.out.println(StrUtils.arrayObjToString(set));

    Assert.assertEquals(size,2);

} 

class   Eras {
    private String start_date;
    private String end_date;
    private String text;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return UtilReflection.toStringRefl(this);
    }
}

} 
