package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.Inspector;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

public class TestInspector {

    private static final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outStream));
    }

    @After
    public void restoreStreams() {
        outStream.reset();
    }

    @Test
    public void testHasSuperClass() {
        new Inspector().inspectSuperClass(String.class, "", false, 0 );
        String expected = "SuperClass [java.lang.Object]";
        assert(outStream.toString().contains(expected));
    }
    
    @Test
    public void testHasInterface() {
        new Inspector().inspectInterface(String.class, ""  ,false, 0);
        String expected = "Interface [java.io.Serializable]";
        assert(outStream.toString().contains(expected));
    }
    
    @Test
    public void testConstructor() {
        new Inspector().inspectConstructor(String.class, "", 0);
        String expected = "Constructor [java.lang.String]";
        assert (outStream.toString().contains(expected));

        expected = "Parameter [int]";
        assert (outStream.toString().contains(expected));

        expected = "Modifier [public]";
        assert (outStream.toString().contains(expected));
    }
    
    @Test
    public void testMethods() {
        new Inspector().inspectMethods(String.class, 0);

        String expected = "Method [length]";
        assert (outStream.toString().contains(expected));

        expected = "Exceptions [java.io.UnsupportedEncodingException]";
        assert (outStream.toString().contains(expected));

        expected = "Parameter [int]";
        assert (outStream.toString().contains(expected));
        expected = "Return [int]";
        assert (outStream.toString().contains(expected));

        expected = "Modifier [public]";
        assert (outStream.toString().contains(expected));

    }
    
    @Test
    public void testFields() {
        new Inspector().inspectFields(String.class, "", false, 0);

        String expected = "Field Name [hash]";
        assert (outStream.toString().contains(expected));

        expected = "Type [int]";
        assert (outStream.toString().contains(expected));

        expected = "Modifier [private]";
        assert (outStream.toString().contains(expected));

        expected = "Value [0]";
        assert (outStream.toString().contains(expected));
    }

    
    


  
}
