import java.util.Arrays;
import java.util.Collections;
import java.util.List;

enum Priority {
    LOWEST,
    LOW,
    NEUTRAL,
    HIGH,
    HIGHEST;

    static Priority[] reversedValues() {
        List<Priority> returnVal = Arrays.asList(Priority.values());
        Collections.reverse(returnVal);
        return returnVal.toArray(new Priority[0]);
    }
}
