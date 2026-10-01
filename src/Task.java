import java.util.*;

class Task {
    private final TaskType type;
    private final List<PriorityAssignment> priorities;
    private final Set<Duplicant> duplicants;
    private final EnumMap<Priority, Integer> priorityAllowance;

    protected Task(TaskType type, Collection<Duplicant> duplicants, EnumMap<Priority, Double> priorityDistribution) {
        this.type = type;

        this.duplicants = new HashSet<>(duplicants);
        this.duplicants.removeIf((d -> d.unableTo(this.type)));

        int dupCount = this.duplicants.size();


        this.priorities = new ArrayList<>(dupCount);

        for (Duplicant duplicant : this.duplicants) {
            this.priorities.add(duplicant.getPriorityAssignment(this.type));
        }


        this.priorityAllowance = new EnumMap<>(Priority.class);
        Priority[] allPriorityValues = Priority.values();
        int assigned = 0;

        for (int i = allPriorityValues.length - 1; i > 0; i--) {
            int prescribed = Math.toIntExact(Math.round(priorityDistribution.get(allPriorityValues[i]) * dupCount)); // All priorities must currently be included in priorityDistribution todo desired behaviour is that the remainder of the duplicants are evenly distributed among undefined priorities
            if (assigned + prescribed > dupCount) prescribed = dupCount - assigned;
            this.priorityAllowance.put(allPriorityValues[i], prescribed);
            assigned += prescribed;
        }
    }

    protected TaskType getType() {
        return this.type;
    }

    /// @param configId
    /// @param deviancy
    /// @return a Collection containing all the modified PriorityAssignment objects
    protected Collection<PriorityAssignment> simplePossibilityAssignment(int configId, int deviancy) {
//        priorities.sort(Comparator.comparing(PriorityAssignment::getDuplicantAbility).reversed()); // Is this a good approach? It should be sorted in descending order
        priorities.sort((p1, p2) -> Integer.compare(p2.getDuplicantAbility(), p1.getDuplicantAbility()));

        int progress = 0;
        for (Priority priority : Priority.reversedValues()) {
            int allowance = priorityAllowance.get(priority);
            List<PriorityAssignment> toAssign = priorities.subList(progress, progress + allowance);
            int duplicantAbility = priorities.get(progress).getDuplicantAbility();

            if (progress != 0) {
                int i = 1;
                while (i <= progress && deviantEquality(deviancy, duplicantAbility, priorities.get(progress - i).getDuplicantAbility())) {
                    toAssign.add(priorities.get(progress - i));
                    i++;
                }
            }

            int i = 1;
            while (i < priorities.size() - progress && deviantEquality(deviancy, duplicantAbility, priorities.get(progress + i).getDuplicantAbility())) {
                toAssign.add(priorities.get(progress + i));
                i++;
            }

            for (PriorityAssignment priorityAssignment : toAssign) {
                priorityAssignment.addPossibility(configId, priority);
            }

            progress += allowance;
        }
        return priorities;
    }

    protected boolean quickCheckConfig(int configId) {
        return false; // todo not yet implemented
    }

    private static boolean deviantEquality(int deviancy, int i1, int i2) {
        return i1 - deviancy <= i2 && i2 <= i1 + deviancy;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Task t)) throw new IllegalArgumentException("Can't compare equality of Task and other types.");
        return type.equals(t.type);
    }
}
