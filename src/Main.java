
private static Set<Duplicant> duplicants;
private static Set<Task> tasks;
private static int attributeRange;

void main() {

    // Placeholder object initializations
    final int DUPLICANT_COUNT = 17;

    duplicants = new HashSet<>(DUPLICANT_COUNT);

    for (int i = 0; i < DUPLICANT_COUNT; i++) {
        duplicants.add(new Duplicant("PLACEHOLDER", new EnumMap<>(AttributeType.class), new HashSet<>()));
    }

    tasks = new HashSet<>(TaskType.values().length); // Silly way of finding the initial length; probably should change

    for (TaskType taskType : TaskType.values()) {
        tasks.add(new Task(taskType, duplicants, new EnumMap<>(Priority.class)));
    }

    Collection<Integer> allAttributes = new HashSet<>();
    for (Duplicant duplicant : duplicants) {
        allAttributes.addAll(duplicant.getAllAttributes().values());
    }
    attributeRange = Collections.max(allAttributes) - Collections.min(allAttributes);
}

static boolean Solver() {
    int configIdCount = 1;
    if (!possibilitySpaceSearch(0, configIdCount)) return false;

    return false; // todo not yet implemented
}

static boolean possibilitySpaceSearch(int deviancy, int configId) {

    return possibilitySearchRecursive(deviancy, configId);
}

static boolean possibilitySearchRecursive(int deviancy, int configId) {
    if (deviancy > attributeRange) return false;
    Collection<PriorityAssignment> affected = new HashSet<>();
    for (Task task : tasks) {
        affected.addAll(task.simplePossibilityAssignment(configId, deviancy));

        if (!task.quickCheckConfig(configId)) {
            invalidConfigCleanup(configId, affected);
            return possibilitySearchRecursive(++deviancy, configId);
        }
    }
    for (Duplicant duplicant : duplicants) {
        if (!duplicant.quickCheckConfig(configId)) {
            invalidConfigCleanup(configId, affected);
            return possibilitySearchRecursive(++deviancy, configId);
        }
    }
    return true;
}

static boolean solutionRealizationSearch(int configId) {
    return false; // todo not yet implemented
}

static void invalidConfigCleanup(int configId, Collection<PriorityAssignment> affected) {
    for (PriorityAssignment priorityAssignment : affected) {
        priorityAssignment.removeId(configId);
    }
}