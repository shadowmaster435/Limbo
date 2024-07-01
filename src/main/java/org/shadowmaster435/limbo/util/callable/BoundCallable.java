package org.shadowmaster435.limbo.util.callable;

public record BoundCallable(Object caller, String method_name, Class<?>[] args, Object... inputs) {

    public <T> T call() {
        T result = null;
        try {
            var method = caller.getClass().getMethod(method_name,args);
            result = (T) ((args.length > 0) ? method.invoke(caller, inputs) : method.invoke(caller));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
