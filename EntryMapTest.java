import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;

import org.junit.Test;

public class EntryMapTest {

	private EntryMap map;

    private TemplateEngine engine;

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }

    @Test
    public void Test1() {
        map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        String result = engine.evaluate("Hello ${name} ${surname}", map,"delete-unmatched");
        assertEquals("Hello Adam Dykes", result);
    }

    @Test
    public void Test2() {
        map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
//////////////////////////////////////////////////////////////////////////////////
    
    @Test
    public void SpecAllOK(){
    	//OK get message right
    	// Return value empty and then plastic
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("Hello ${name} ${Name}", map,"delete-unmatched");
    	//Saving them as case insensitive these 2 are considred the same
    	assertEquals("Hello Adam Dykes",result);    	
    }
    
    // Spec 1
    @Test (expected = RuntimeException.class)
    public void Spec1TemplateNull(){
    	try{
    		map.store(null, "Adam", true);
    		fail("Runtime Exception not Caught");
    	}catch(Exception e){
    		assertEquals(RuntimeException.class,e.getCause().getClass());
    	}    	
    }
    
    @Test (expected = RuntimeException.class)
    public void Spec1TemplateEmpty(){
    	// Runtime exception
    	try{
    		map.store("", "Adam", true);
    		fail("Runtime Exception not Caught");
    	}catch(Exception e){
    		assertEquals(RuntimeException.class,e.getCause().getClass());
    	}
    }
    
    // Spec 2
    @Test (expected = RuntimeException.class)
    public void Spec2ReplaceValueNull(){    
    	//Runtime exception
    	try{
    		map.store("type", null, true);
    		fail("Runtime Exception not Caught");
    	}catch(Exception e){
    		assertEquals(RuntimeException.class,e.getCause().getClass());
    	}
    }
    
    // Spec 3   
    @Test
    public void Spec3CaseSensitiveNull(){
    	// Defaults to case insensitive -> check it
    	 map.store("name", "Adam", null);
        map.store("surname", "Dykes", null);
        String result = engine.evaluate("Hello ${Name} ${Surname}", map,"delete-unmatched");
    	assertEquals("Hello Adam Dykes", result);
    	
    }
    
    @Test
    public void Spec3CaseSensitiveOptional(){
    	// Defaults to case insensitive -> check it
    	 map.store("name", "Adam");
        map.store("surname", "Dykes");
        String result = engine.evaluate("Hello ${Name} ${Surname}", map,"delete-unmatched");
    	assertEquals("Hello Adam Dykes", result);
    	
    }
    
    // Spec 4
    
    @Test
    public void Spec4isOrdered(){
    	// Since Name is case sensitive that means 
    	map.store("name", "Adam", false);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("Hello ${Name} ${Name}", map,"delete-unmatched");
        assertEquals("Hello Adam Adam", result);
    }
    
    // Spec 5
    
    @Test
    public void Spec5SameEntries(){
    	map.store("Name", "Adam", true);
        map.store("Name", "Adam", true);
        assertEquals(1,map.getEntries().size());
    }
    
    @Test
    public void Spec5DifferentEntries(){
    	map.store("Name", "Adam", false);
        map.store("Name", "Adam", true);
        assertEquals(2,map.getEntries().size());
    }

}
