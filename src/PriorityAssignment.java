import java.util.*;

class PriorityAssignment {

    private final Duplicant duplicant;
    private final TaskType taskType;

    private final HashMap<Integer, LinkedHashSet<Priority>> possibilities;

    protected PriorityAssignment(Duplicant duplicant, TaskType taskType) {
        this.duplicant = duplicant;
        this.taskType = taskType;

        this.possibilities = new HashMap<>();
    }

    protected TaskType getTaskType() {
        return taskType;
    }

    protected int getDuplicantAbility() {
        return duplicant.getTaskAbility(taskType);
    }

    protected Priority getPriority(int id) {
        if (!isDetermined(id)) throw new IllegalStateException("Can't get the priority before it is determined.");
        return possibilities.get(id).getFirst();
    }

    protected void addPossibility(int id, Priority priority) {
        if (!possibilities.containsKey(id)) possibilities.put(id, new LinkedHashSet<>(5));
        possibilities.get(id).add(priority);
    }

    protected void removePossibility(int id, Priority priority) {
        if (!possibilities.containsKey(id)) throw new IllegalArgumentException("Unable to remove priority because id does not exist.");
        possibilities.get(id).remove(priority);
    }

    protected List<Priority> getPossibilities(int id) {
        if (!possibilities.containsKey(id)) throw new IllegalArgumentException("Unable to get priorities because id does not exist.");
        List<Priority> priorityList = new ArrayList<>(possibilities.get(id)); // O(1) because possibilities.size() < 6
        priorityList.sort(Comparator.reverseOrder());
        return priorityList;
    }

    protected void removeId(int id) {
        this.possibilities.remove(id);
    }

    protected boolean isDetermined(int id) {
        if (!possibilities.containsKey(id)) return false;
        return possibilities.get(id).size() == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PriorityAssignment pr)) throw new IllegalArgumentException("Can't compare equality of type PriorityAssignment with other types.");
        return duplicant.equals(pr.duplicant) && taskType.equals(pr.taskType);
    }

    @Override
    public int hashCode() {
        return duplicant.hashCode() * taskType.hashCode();
    }
}
