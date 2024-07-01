package org.shadowmaster435.limbo.util.callable;

public record Condition(String method_name, Class<?>... args) {
    public boolean test() {
        try {
            var caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            var method = caller.getMethod(method_name,args);
            return (boolean) ((args.length > 0) ? method.invoke(caller, (Object) args) : method.invoke(caller));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
