import java.util.*;

class Duplicant {

    private final String name;
    private final EnumMap<AttributeType, Integer> attributes;
    private final EnumMap<TaskType, PriorityAssignment> priorities;
    private final HashSet<TaskType> unableTo;

    protected Duplicant(String name, EnumMap<AttributeType, Integer> attributes, HashSet<TaskType> unableTo) {
        this.name = name;
        this.attributes = attributes;
        this.unableTo = unableTo;

        this.priorities = new EnumMap<>(TaskType.class);

        for (TaskType task : TaskType.values()) {
            if (this.unableTo.contains(task)) continue;
            this.priorities.put(task, new PriorityAssignment(this, task));
        }
    }

    protected boolean unableTo(TaskType type) {
        return unableTo.contains(type);
    }

    protected PriorityAssignment getPriorityAssignment(TaskType type) {
        return priorities.get(type);
    }

    private int getAttributeStat(AttributeType attribute) {
        return this.attributes.get(attribute);
    }

    protected Map<AttributeType, Integer> getAllAttributes() {
        return attributes;
    }

    protected int getTaskAbility(TaskType type) {
        return this.getAttributeStat(type.getAttribute());
    }

    protected String getName() {
        return name;
    }

    protected int getResponsibilityScore(int configId) {
        int score = 0;
        int divisor = 0;
        for (PriorityAssignment priorityAssignment : priorities.values()) {
            if (!priorityAssignment.isDetermined(configId)) continue;
            int priorityLevel = priorityAssignment.getPriority(configId).ordinal();
            divisor += priorityLevel;
            score += priorityLevel * getTaskAbility(priorityAssignment.getTaskType());
        }
        if (divisor == 0) return 0;
        return score / divisor;
    }

    protected boolean quickCheckConfig(int configId) {
        return false; // TODO not yet implemented
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Duplicant d)) throw new IllegalArgumentException("Cannot compare equality of type Duplicant with other object types.");
        return name.equals(d.name) && attributes.equals(d.attributes);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * attributes.hashCode();
    }
}