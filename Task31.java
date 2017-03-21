package test_Suites;

import static org.junit.Assert.*;

import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import main_Project.EntryMap;
import main_Project.TemplateEngine;

public class Task31 {

	private EntryMap map;

    private TemplateEngine engine;
    

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }
	 
	 /*////////////////////////*/
	 
	 //Spec1
	 @Test(expected = RuntimeException.class)
	    public void TestEmptyTemplateF() {
	        map.store("", "Jimmy", false);
	    }

	 @Test(expected = RuntimeException.class)
	    public void TestNullTemplateF() {
	        map.store(null, "Jimmy", false);
	    }
	 
	 @Test(expected = RuntimeException.class)
	    public void TestEmptyTemplateT() {
	        map.store("", "Jimmy", true);
	    }

	 @Test(expected = RuntimeException.class)
	    public void TestNullTemplateT() {
	        map.store(null, "Jimmy", true);
	    }
	 
	 //Spec2
	 @Test(expected = RuntimeException.class)
	    public void TestNullReplaceF() {
	        map.store("name", null, false);
	    } 
	 @Test(expected = RuntimeException.class)
	    public void TestNullReplaceT() {
	        map.store("name", null, true);
	    } 
	 
	 //Spec3
	 @Test
	    public void TestNullFlag() {
	        map.store("name", "Adam", null);
	        map.store("surname", "Dykes", null);
	        String result = engine.evaluate("Hello ${NaMe} ${SuRnAmE}", map,"delete-unmatched");
	        assertEquals("Hello Adam Dykes", result);
	    }
	 
	 //Spec4
    @Test
	    public void TestOrder(){
	    	map.store("name", "Adam", true);
	        map.store("Name", "Dykes", false);
	        String result = engine.evaluate("Hello ${name} ${Name}", map,"keep-unmatched");
	    	assertEquals("Hello Adam Dykes",result);    
	    }

	 //Spec5
	 @Test
	    public void TestDuplicateEntriesF() {
		 map.store("name", "Adam", false);
         map.store("name", "Adam", false);
         assertEquals(map.getEntries().size(), 1);
	    }
	 @Test
	    public void TestDuplicateEntriesT() {
		 map.store("name", "Adam", true);
	     map.store("name", "Adam", true);
	     assertEquals(map.getEntries().size(), 1);
	    }
	 @Test
	    public void TestDuplicateEntriesDifferentFlags() {
		 map.store("name", "Adam", false);
	     map.store("name", "Adam", true);
         assertEquals(map.getEntries().size(), 2);
	    } 
	 
	 /////////////////////////////
	 //////////ENGINE/////////////
	 /////////////////////////////
	 
	    // Spec 1
	    @Test
	    public void Spec1TempNullDU(){
	    	// Template can be NULL 
	    	// Unchanged string should be returned (in this case null is returned back)
	    	map.store("name", "Adam", true);
	        map.store("Name", "Dykes", true);
	        String result = engine.evaluate(null, map,"delete-unmatched");
	    	assertEquals(null,result);   	
	    }
	    @Test
	    public void Spec1TempEmptyDU(){
	    	// Template can be empty
	    	// Unchanged string should be returned (in this case <empty string> returned back)
	    	map.store("name", "Adam", true);
	        map.store("Name", "Dykes", true);
	        String result = engine.evaluate("", map,"delete-unmatched");
	    	assertEquals("",result);   	
	    }
	    
	    @Test
	    public void Spec1TempNullKU(){
	    	// Template can be NULL 
	    	// Unchanged string should be returned (in this case null is returned back)
	    	map.store("name", "Adam", true);
	        map.store("Name", "Dykes", true);
	        String result = engine.evaluate(null, map,"keep-unmatched");
	    	assertEquals(null,result);   	
	    }
	    @Test
	    public void Spec1TempEmptyKU(){
	    	// Template can be empty
	    	// Unchanged string should be returned (in this case <empty string> returned back)
	    	map.store("name", "Adam", true);
	        map.store("Name", "Dykes", true);
	        String result = engine.evaluate("", map,"keep-unmatched");
	    	assertEquals("",result);   	
	    }
	    // Spec 2
	    @Test
	    public void Spec2MapNulDUl(){
	    	// Entry Map can be NULL
	    	// Unchanged string should be returned.
	        String result = engine.evaluate("Hello ${name} ${Name}", null,"delete-unmatched");
	    	assertEquals("Hello ${name} ${Name}",result);   
	    }
	    @Test
	    public void Spec2MapNulKUl(){
	    	// Entry Map can be NULL
	    	// Unchanged string should be returned.
	        String result = engine.evaluate("Hello ${name} ${Name}", null,"keep-unmatched");
	    	assertEquals("Hello ${name} ${Name}",result);   
	    }
	    
	    // Spec 3
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
	    	// Matching mode cannot be EMPTY -> default = delete-unmatched
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
	    	map.store("$", "loves", true);
	    	map.store("hahahaha hohohohoho", "cheesy", true);
	        map.store("\n", "bangos", false);
	        String result = engine.evaluate("${$} ${hahahaha hohohohoho} ${\n}", map,"keep-unmatched");
	    	assertEquals("loves cheesy bangos",result);    
	    }
	    
	    @Test
	    public void Spec4_2(){
	    	// Everything should be between ${ and }
	    	// Templates boundaries are omitted
	    	map.store("name", "Adam", true);
	        map.store("Name", "Dykes", false);
	        String result = engine.evaluate("Hello ${name} ${Name}", map,"keep-unmatched");
	    	assertEquals("Hello Adam Dykes",result);    
	    }
	    	    
	    // Spec 5
	    @Test
	    public void Spec5IgnoreNonChar(){
	        map.store("Name surname", "Dykes", false);
	        String result = engine.evaluate("${Name     surname} ${Namesurname} ${Name surname}", map,"keep-unmatched");
	    	assertEquals("Dykes Dykes Dykes",result);  
	    }
	    
	    // Spec 6
	    @Test
	    public void Spec6(){
	    	// Everything should be between ${ and }
	    	// Templates boundaries are omitted
	    	map.store("${", "Adam", true);
	    	map.store("$", "loves", true);
	    	map.store("hahahaha hohohohoho", "cheesy", true);
	        map.store("\n", "bangos", false);
	        String result = engine.evaluate("${${} ${$} ${hahahaha hohohohoho} ${\n}", map,"keep-unmatched");
	    	assertEquals("Adam loves cheesy bangos",result);    
	    }
	    @Test
	    public void Spec6_2(){
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
	    	// Template order:
	    	//		1. ${n}
	    	//		2.${sur${n}} (left-right)
	    	//		3.${bur${n}} (left-right)
	    	//
	    	// n - gets replaced first with ns
	    	//
	    	// News Templates:
	    	//		1. ${surns} (left-right)
	    	//		2. ${burns} (left-right)
	    	// 
	    	// surns - gets replaced with Jameson
	    	// burns - gets replaced with Browns
	    	//
	    	// If it was any other order (left-right or big-small) sur would get
	    	// replaced first with Smith and the new template to be matched would
	    	// have to be Smithns. Which proves that the order is small to big.
	    	
	        map.store("surns","Jameson",false);
	        map.store("sur","Smith",false);
	        map.store("bur","Brown", false);
	        map.store("buns","Browns", false);
	        map.store("n","ns", false);

	        String result = engine.evaluate("Hello ${sur${n}} ${bu${n}}", map,"delete-unmatched");
	    	assertEquals("Hello Jameson Browns",result); 
	    }
	   
	    
	    // Spec 8
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
	    
	    
	    
	    //Additional Tests for extra coverage
	    
	    //tests for equals on template engine.
	    
	    @Test
	    public void twoequalTemplates(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	Boolean result = a.equals(a);
	    	assertEquals(true,result); 
	    }
	    
	    @Test
	    public void nulltwo(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	Boolean result = a.equals(null);
	    	assertEquals(false,result); 
	    }
	    
	    
	    @Test
	    public void twoUnequalTemplates(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	TemplateEngine.Template b = engine.new Template(null,10,null);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoUnequalTemplates2(){
	    	TemplateEngine.Template a = engine.new Template(null,10,null);
	    	TemplateEngine.Template b = engine.new Template(null,null,null);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoUnequalTemplates3(){
	    	TemplateEngine.Template a = engine.new Template(10,10,null);
	    	TemplateEngine.Template b = engine.new Template(10,null,null);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoUnequalTemplates4(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	TemplateEngine.Template b = engine.new Template(10,null,null);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoequalTemplates5(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	TemplateEngine.Template b = engine.new Template(null,null,null);
	    	Boolean result = a.equals(b);
	    	assertEquals(true,result); 
	    }
	    
	    @Test
	    public void twoequalTemplates6(){
	    	TemplateEngine.Template a = engine.new Template(null,null,"hey");
	    	TemplateEngine.Template b = engine.new Template(null,null,"hey");
	    	Boolean result = a.equals(b);
	    	assertEquals(true,result); 
	    }
	    
	    @Test
	    public void twounequalTemplates7(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	TemplateEngine.Template b = engine.new Template(null,null,"hey");
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoequalTemplates8(){
	    	TemplateEngine.Template a = engine.new Template(null,5,null);
	    	TemplateEngine.Template b = engine.new Template(null,5,null);
	    	Boolean result = a.equals(b);
	    	assertEquals(true,result); 
	    }
	    
	    @Test
	    public void twoUnequalIndices(){
	    	TemplateEngine.Template a = engine.new Template(5,null,null);
	    	TemplateEngine.Template b = engine.new Template(15,null,null);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void oneTemplateCompNontemplate(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	String b = "hello";
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    
	    //Entry map equals
	    
	    @Test
	    public void twoequalEntrys(){
	    	EntryMap.Entry a = map.new Entry("hey","adam",null);
	    	Boolean result = a.equals(a);
	    	assertEquals(true,result); 
	    }
	    
	    @Test
	    public void nulltwoEntrys(){
	    	EntryMap.Entry a = map.new Entry(null,null,null);
	    	Boolean result = a.equals(null);
	    	assertEquals(false,result); 
	    }
	    
	    
	    @Test
	    public void twoUnequalEntrys(){
	    	EntryMap.Entry a = map.new Entry("hey","adam",null);
	    	EntryMap.Entry b = map.new Entry("hey","bob",null);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoUnequalEntrys2(){
	    	EntryMap.Entry a = map.new Entry("heythere","bob",null);
	    	EntryMap.Entry b = map.new Entry("hey","bob",null);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoUnequalEntrys3(){
	    	EntryMap.Entry a = map.new Entry("hey","bob",null);
	    	EntryMap.Entry b = map.new Entry("hey","bob",true);
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void twoUnequalEntrys4(){
	    	EntryMap.Entry a = map.new Entry("hey","bob",null);
	    	EntryMap.Entry b = map.new Entry("hey","bob",null);
	    	Boolean result = a.equals(b);
	    	assertEquals(true,result); 
	    }
	    
	    
	    @Test
	    public void oneEntryCompNonEntry(){
	    	EntryMap.Entry a = map.new Entry("heythere","bob",null);
	    	String b = "hello";
	    	Boolean result = a.equals(b);
	    	assertEquals(false,result); 
	    }
	    
	    
	    
	    //tests for hashcode
	    
	    @Test
	    public void hashcodetest(){
	    	TemplateEngine.Template a = engine.new Template(null,null,null);
	    	int x = a.hashCode();
	    	Boolean result = x==0;
	    	assertEquals(true,result); 
	    }
	    
	    @Test
	    public void hashcodetest2(){
	    	EntryMap.Entry a = map.new Entry("hey","adam",null);
	    	int x = a.hashCode();
	    	Boolean result = x==0;
	    	assertEquals(false,result); 
	    }
	    
	    @Test
	    public void gettemplatesreplaced(){
	    	TemplateEngine.Result a = engine.new Result(null,5);
	    	int x = a.getTemplatesReplaced();
	    	Boolean result = x==5;
	    	assertEquals(true,result); 
	    }
	    
	    
/////////////////////////////////////////////////////////////////////////////////////
//
//  NewTemplate Engine Spec - Add Optimization matching mode
//
/////////////////////////////////////////////////////////////////////////////////////
	    
	    
	    @Test
	    public void highLevelNestedTemplates(){
	    	// The highest level of the nested template is missing from the map
	    	// keep-unmatched will replace all the lower ones and  just keep the 
	    	// highest one without replacing it.
	    	// On the other hand delete-unmatched will delete all templates contained within
	    	// resulting in fewer replacements
	    	// keep-unmatched selected
	    	
	    	//map.store("name Dykes", "Adam", true);
	        map.store("sur Brown", "Dykes", true);
	        map.store("type","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name${sur ${type}}}", map,"keep-unmatched");
	        String result = engine.evaluate("Hello ${name${sur ${type}}}", map,"optimization");
	    	assertEquals(expectedResult,result); 
	    }
	    
	    @Test
	    public void lowLevelNestedTemplates(){
	    	// The lowest level of the nested templates does not exist. 
	    	// Therefore having the templates right should not replace anything
	    	// And both methods should replace the same amount of template
	    	// Which means the program should default in keep-unmatched
	    	map.store("name Dykes", "Adam", true);
	    	map.store("sur Brown", "Dykes", true);
	        //map.store("type","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name${sur ${type}}}", map,"keep-unmatched");
	        String result = engine.evaluate("Hello ${name${sur ${type}}}", map,"optimization");
	    	assertEquals(expectedResult,result); 
	    }
	    
	    @Test
	    public void middleLevelNestedTemplates(){
	    	// The middle level of the nested templates does not exist. 
	    	// Therefore having the templates right should replace the first only string
	    	// giving keep-unmatched an extra template
	    	// keep-unmatched selected
	    	map.store("name Dykes", "Adam", true);
	    	//map.store("sur Brown", "Dykes", true);
	        map.store("type","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name${sur ${type}}}", map,"keep-unmatched");
	        String result = engine.evaluate("Hello ${name${sur ${type}}}", map,"optimization");
	    	assertEquals(expectedResult,result); 
	    }
	    
	    @Test
	    public void simpleTemplateMissing(){
	    	// Without any nested loop and having a template missing
	    	// bothe mathching cases should replace the same number of
	    	// tempates -> Default is keep-unmatched
	    	map.store("name", "Adam", true);
	        //map.store("sur", "Dykes", true);
	        map.store("type","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name} ${sur} ${type}", map,"keep-unmatched");
	        String result = engine.evaluate("Hello ${name} ${sur} ${type}", map,"optimization");
	    	assertEquals(expectedResult,result); 
	    }
	    
	    @Test
	    public void malformedTemplate(){
	    	// Opening a template with $ but not closing it will result in
	    	// keep-unmatched keeping the template and in delete-unmatched
	    	// deleting it an translating type right giving an extra template
	    	// delete-unmatched
	    	// Note: Since the code already defualts in delete-unmatched this will produce no error here
	    	map.store("name", "Adam", true);
	        map.store("sur", "Dykes", true);
	        map.store("type","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name} ${sur} ${type$}", map,"delete-unmatched");
	        String result = engine.evaluate("Hello ${name} ${sur} ${type$}", map,"optimization");
	    	assertEquals(expectedResult,result); 
	    }
	    
	    @Test
	    public void simpleNestedTemplate(){
	    	// If you accidentally slip an extra template without adding it to the map it will get deleted
	    	// Deleting the nested template will give delete-unmatched an extra case
	    	// delete-unmatched
	    	// Note: Since the code already defualts in delete-unmatched this will produce no error here
	    	map.store("name", "Adam", true);
	        map.store("sur", "Dykes", true);
	        map.store("type","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name} ${sur} ${type${blah}}", map,"delete-unmatched");
	        String result = engine.evaluate("Hello ${name} ${sur} ${type${blah}}", map,"optimization");
	    	assertEquals(expectedResult,result); 
	    }
	    
	    @Test
	    public void nonReplaceableTemplates(){
	    	// When having nested templates you have to take into account to have as 
	    	// templates not name but Name Dykes for the value to be replaced. If however 
	    	// 
	    	map.store("name", "Adam", true);
	        map.store("sur", "Dykes", true);
	        map.store("type","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name} ${sur} ${type${}}", map,"delete-unmatched");
	        String result = engine.evaluate("Hello ${name} ${sur} ${type${}}", map,"optimization");
	    	assertEquals(expectedResult,result); 
	    }
	    
	    @Test
	    public void templateAsPartOfString(){
	    	// Engine processes one template at a time and attempts to match it against the keys of the entry map
	    	// until there is a match or the entry list is exhausted
	    	map.store("name", "Adam", true);
	        map.store("sur ${type}", "Dykes", true);
	        map.store("types","Brown", false);
	        String expectedResult = engine.evaluate("Hello ${name} ${sur ${type}}", map,"keep-unmatched");
	        String result = engine.evaluate("Hello ${name} ${sur ${type}}", map,"optimization");
	    	assertEquals("Hello Adam Dykes",result); 
	    	assertEquals(expectedResult,result); 
	    }

}
