package ua.university;

import java.time.LocalDate;
import java.util.Objects;

public class Assignment {
    private final Module module;
    private final LocalDate dueDate;
    private final int maxPoints;
    private final AssignmentType type;

    public Assignment(Module module, LocalDate dueDate, int maxPoints, AssignmentType type) {
        this.module = Objects.requireNonNull(module);
        this.dueDate = Objects.requireNonNull(dueDate);
        if (maxPoints <= 0) throw new IllegalArgumentException("maxPoints must be > 0");
        this.maxPoints = maxPoints;
        this.type = Objects.requireNonNull(type);
    }

    public Module getModule() { return module; }
    public LocalDate getDueDate() { return dueDate; }
    public int getMaxPoints() { return maxPoints; }
    public AssignmentType getType() { return type; }

    @Override
    public String toString() {
        return "Assignment{" + module.title() + ", due=" + dueDate + ", max=" + maxPoints + ", type=" + type +"}";
    }
}
