package st;

import static org.junit.Assert.*;

import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;


public class Task1 {
	
    private st.EntryMap map;

    private st.TemplateEngine engine;
    

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }
    // For M_0
    @Test
    public void deletesunmatchedempty(){
    	map.store("d", "Adam", true);
        String result = engine.evaluate("${}${d}", map,"delete-unmatched");
    	assertEquals("Adam",result); 
    	
    }
    //For M_1
    @Test
    public void booleannotReset(){
    	map.store("d", "Adam", true);
        String result = engine.evaluate("$A{d}", map,"delete-unmatched");
    	assertEquals("$A{d}",result); 
    	
    }
    //For M_2
    @Test
    public void doesntcatchafterTemplateDeleted(){
    	map.store("d", "Adam", true);
        String result = engine.evaluate("${}e", map,"delete-unmatched");
    	assertEquals("e",result); 
    	
    }
    // For M_3
 	    @Test
    public void Spec4(){
    	// Everything should be between ${ and }
    	// Templates boundaries are omitted
    	map.store("$", "loves", true);
    	map.store("hahahaha hohohohoho", "cheesy", true);
        map.store("\n", "bangos", false);
        String result = engine.evaluate("${$} ${hahahaha hohohohoho} ${\n}", map,"keep-unmatched");
    	assertEquals("loves cheesy bangos",result);    
    }
	 
	 //for M_4
	 @Test
	    public void TestDuplicateEntriesF() {
		 map.store("name", "Adam", false);
         map.store("name", "Adam", false);
         assertEquals(map.getEntries().size(), 1);
	    }
	    
	// for M_5
    @Test
    public void Spec4_2(){
    	// Everything should be between ${ and }
    	// Templates boundaries are omitted
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", false);
        String result = engine.evaluate("Hello ${name} ${Name}", map,"keep-unmatched");
    	assertEquals("Hello Adam Dykes",result);    
    }
	 
	 //For M_6
    @Test
    public void Spec3MatchEmpty(){
    	// Matching mode cannot be EMPTY -> default = delete-unmatched
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("Hello ${name} ${Name ${type}}", map,"");
    	assertEquals("Hello Adam Dykes",result);       	
    }
    
    //For M_7
    @Test
    public void Spec8DeleteUnmatched(){
    	// Engine processes one template at a time and attempts to match it against the keys of the entry map
    	// until there is a match or the entry list is exhausted
    	map.store("name", "Adam", true);
        map.store("sur Brown", "Dykes", true);
        map.store("type","Brown", false);
        String result = engine.evaluate("Hello ${name} ${sur ${type}} ${type} ${tt}", map,"delete-unmatched");
    	assertEquals("Hello Adam Dykes Brown ",result); 
    	
    }
    
    //For M_8
    @Test
    public void Stringooboundserror(){
    	map.store("d", "Adam", true);
    	map.store("de", "Adam", true);
    	map.store("dAdam", "hey!", true);
        String result = engine.evaluate("${d${${dAdam}}}", map,"delete-unmatched");
    	assertEquals("Adam",result); 
    }
    
    //For M_9
    @Test
    public void Spec8KeepUnmatched(){
    	// Engine processes one template at a time and attempts to match it against the keys of the entry map
    	// until there is a match or the entry list is exhausted
    	map.store("name", "Adam", true);
        map.store("sur Brown", "Dykes", true);
        map.store("type","Brown", false);
        String result = engine.evaluate("Hello ${name} ${sur ${type}} ${type} ${tt}", map,"keep-unmatched");
    	assertEquals("Hello Adam Dykes Brown ${tt}",result); 
    	
    }
    
    
}
	    
	    
