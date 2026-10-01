enum TaskType {
    ATTACKING(AttributeType.EXCAVATION),
    LIFE_SUPPORT(AttributeType.LIFE_SUPPORT),
    TOGGLING(AttributeType.TOGGLE),
    DOCTORING(AttributeType.MEDICINE),
    TIDYING(AttributeType.STRENGTH),
    COOKING(AttributeType.CUISINE),
    DECORATING(AttributeType.CREATIVITY),
    RESEARCHING(AttributeType.SCIENCE),
    OPERATING(AttributeType.MACHINERY),
    FARMING(AttributeType.AGRICULTURE),
    RANCHING(AttributeType.HUSBANDRY),
    BUILDING(AttributeType.CONSTRUCTION),
    DIGGING(AttributeType.EXCAVATION),
    SUPPLYING(AttributeType.STRENGTH),
    STORING(AttributeType.STRENGTH);

    private final AttributeType attribute;

    TaskType(AttributeType attribute) {
        this.attribute = attribute;
    }

    public AttributeType getAttribute() {
        return this.attribute;
    }
}
