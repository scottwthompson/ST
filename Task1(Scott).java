import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import st.EntryMap;
import st.TemplateEngine;
import st.EntryMap;
import st.TemplateEngine;

public class Task1 {
	
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
	 //Spec1
	 @Test(expected = RuntimeException.class)
	    public void Test3() {
	        map.store("", "Jimmy", false);
	    }
	 //Spec2
	 @Test(expected = RuntimeException.class)
	    public void Test4() {
	        map.store(null, "Jimmy", false);
	    }
	 //Spec3
	 @Test
	    public void Test5() {
	        map.store("name", "Adam", null);
	        map.store("surname", "Dykes", null);
	        String result = engine.evaluate("Hello ${NaMe} ${SuRnAmE}", map,"delete-unmatched");
	        assertEquals("Hello Adam Dykes", result);
	    }
	 @Test
	    public void Test6() {
	        map.store("name", "Adam");
	        map.store("surname", "Dykes");
	        String result = engine.evaluate("Hello ${NaMe} ${SuRnAmE}", map,"delete-unmatched");
	        assertEquals("Hello Adam Dykes", result);
	    }
	 
	 //Spec4
	 @Test
	    public void Test7() {
	        map.store("name", "Adam");
	        map.store("surname", "Dykes");
	        String result = engine.evaluate("Hello ${NaMe} ${SuRnAmE}", map,"delete-unmatched");
	        assertEquals("Hello Adam Dykes", result);
	    }
	 //Spec5
	 @Test
	    public void Test8() {
		 map.store("name", "Adam", false);
         map.store("name", "Adam", false);
         assertEquals(map.getEntries().size(), 1);
	    }
	 @Test
	    public void Test9() {
		 map.store("name", "Adam", false);
	     map.store("name", "Adam", true);
	     
	    } 
	 
	 


}
