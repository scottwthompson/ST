import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;

public class TemplateEngineTest {

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
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("Hello ${name} ${Name ${type}}", map,"keep-unmatched");
    	assertEquals("Hello Adam ${Name ${type}}",result);    	
    }
    
    // Spec 1
    @Test
    public void Spec1TempNull(){
    	// Template can be NULL 
    	// Unchanged string should be returned (in this case null is returned back)
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate(null, map,"delete-unmatched");
    	assertEquals(null,result);   	
    }
    @Test
    public void Spec1TempEmpty(){
    	// Template can be empty
    	// Unchanged string should be returned (in this case <empty string> returned back)
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("", map,"delete-unmatched");
    	assertEquals("",result);   	
    }
    
    // Spec 2
    @Test
    public void Spec2MapNull(){
    	// Entry Map can be NULL
    	// Unchanged string should be returned.
        String result = engine.evaluate("Hello ${name} ${Name}", null,"delete-unmatched");
    	assertEquals("Hello ${name} ${Name}",result);   
    	
    }
    
    // Spec 3
    // See SpecAllOK test case to see how it looks like how the keep-unmatched string looks like
    // In that case name is not matched but in delete-unmatched case everything is matched perfectly
    @Test
    public void Spec3MatchNull(){
    	// Matching mode cannot be NULL -> default = delete-unmatched
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("Hello ${name} ${Name ${type}}", map,null);
    	assertEquals("Hello Adam Dykes",result);       	
    }
    
    @Test
    public void Spec3MatchEmpty(){
    	// Matching mode cannot be NULL -> default = delete-unmatched
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("Hello ${name} ${Name ${type}}", map,"");
    	assertEquals("Hello Adam Dykes",result);       	
    }
    
    @Test
    public void Spec3MatchWrongText(){
    	// Matching mode cannot be NULL -> default = delete-unmatched
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", true);
        String result = engine.evaluate("Hello ${name} ${Name ${type}}", map,"delete-unmched");
    	//Saving them as case insensitive these 2 are considered the same
    	assertEquals("Hello Adam Dykes",result);       	
    }
    
    // Spec 4
    @Test
    public void Spec4(){
    	// Everything should be between ${ and }
    	// Templates boundaries are omitted
    	map.store("name", "Adam", true);
        map.store("Name", "Dykes", false);
        String result = engine.evaluate("Hello ${name} ${Name}", map,"keep-unmatched");
    	assertEquals("Hello Adam Dykes",result);    
    	
    }
    
    // Spec 5
    @Test
    public void Spec5(){
    	// ${middle name} = ${middlename}
    	map.store("name", "Adam", true);
        map.store("Name suname", "Dykes", false);
        String result = engine.evaluate("Hello ${name} ${Name     surname}", map,"keep-unmatched");
    	assertEquals("Hello Adam Dykes",result);  
    }
    
    
    // Spec 6
    @Test
    public void Spec6(){
    	// Nested ${${}} check
    	map.store("name", "Adam", true);
        map.store("sur Brown orange name", "Dykes", true);
        map.store("colour2","orange", false);
        map.store("colour","Brown", false);
        String result = engine.evaluate("Hello ${name} ${sur ${colour} ${colour2} name}", map,"keep-unmatched");
    	assertEquals("Hello Adam Dykes",result); 
    	
    }
    
    // Spec 7
    @Test
    public void Spec7(){
    	// Shorter templates are processed first
    	map.store("name", "Adam", true);
        map.store("N ${type}", "Dykes", true);
        map.store("type","Brown", false);
        String result = engine.evaluate("Hello ${name} ${N ${type}}", map,"keep-unmatched");
    	assertEquals("Hello Adam ${N${type}}",result); 
    	
    }
    
    // Spec 8
    @Test
    public void Spec8(){
    	// Engine processes one templrate at a time and attempts to match it againt the keys of the entry map
    	// until there is a match or the entry list is exhausted
    	
    }
    
    // Spec EMpty
    @Test
    public void SpecEmpty(){
    	
    }
}
