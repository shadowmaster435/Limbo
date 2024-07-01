package org.shadowmaster435.limbo.util.functions;

public interface StringFunctions {
    default String name_from_snake(String snake_case_name) {
        var spaced = snake_case_name.replace("_", " ");
        StringBuilder result = new StringBuilder();
        var space_found = true;
        for (int i = 0; i < spaced.length(); ++i) {
            var let = String.valueOf(spaced.charAt(i));
            if (space_found && !let.equals(" ")) {
                result.append(let.toUpperCase());
                space_found = false;
            } else {
                result.append(let);
                if (let.equals(" ")) {
                    space_found = true;
                }
            }
        }
        return result.toString();
    }

}
