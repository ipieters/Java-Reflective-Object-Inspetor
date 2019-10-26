package src;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	printHelper("Class Name is ["+ c.getName() +"]",0);
    	
    	inspectSuperClass(c, obj, recursive, depth);
    	
    }
    
    public void inspectSuperClass(Class c, Object obj, boolean recursive, int depth) {

        if (c.equals(Object.class))
            return;
        Class superClass = c.getSuperclass();

        if (superClass != null) {
        	printHelper("SuperClass Name is [" + superClass.getName() +"]", depth);
            inspectClass(superClass, obj, recursive, depth+1);
        } else
            printHelper("There is no super class", depth);
        
    }
    

    public void getInterfaceInfo(Class c, Object obj, boolean recursive, int depth) {

        Class[] interfaces = c.getInterfaces();

        for (Class inter: interfaces) {
            printHelper("Interface Name is [ " + inter.getName() + "]", depth);
            inspectClass(inter, obj, recursive, depth + 1);
        }
    }


    public void printHelper(String message, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        System.out.println(message);
    }
}
