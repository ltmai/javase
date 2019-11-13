import java.util.Arrays;
import java.util.List;

/**
 * Mediator pattern: restricts the direct communication between
 * components and forces them to collaborate via a mediator.
 * 
 * This decouples the dependencies between components and puts
 * the event handling logic at a central place.
 */
public class MediatorPattern {

    /**
     * Helper class that serves as an event type
     */
    public static class Employee {
        private String name;

        public Employee(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Department
     * The abstract department that provides common communication logic
     * between departments. The default behavior when department is
     * notified of event is doing nothing. The concrete Department must
     * override this.
     * Abstract Department may have other notified(Event) methods for 
     * types of events other than Employee, e.g. Order.
     */
    public static abstract class Department {
        protected Mediator mediator;

        public Department(Mediator mediator) {
            this.mediator = mediator;
        }

        /**
         * Default event handler for this event type
         * @param employee
         */
        public void notified(Employee employee) {
        }
    }

    /**
     * Concrete Department : Human Resource
     */
    public static class HRDepartment extends Department {

        public HRDepartment(Mediator mediator) {
            super(mediator);
        }

        public void hire(Employee employee) {
            System.out.println("[HR] Hire new employee " + employee.getName());
            mediator.notify(this, employee);
        }
    }

    /**
     * Concrete Department : IT
     */
    public static class ITDepartment extends Department {
        public ITDepartment(Mediator mediator) {
            super(mediator);
        }

        @Override
        public void notified(Employee employee) {
            System.out.println("[IT] Create new user for " + employee.getName());
        }
    }

    /**
     * Concrete Department : Warehouse
     */
    public static class WHDepartment extends Department {
        public WHDepartment(Mediator mediator) {
            super(mediator);
        }
    }

    /**
     * DepartmentMediator
     * Concrete mediator, can be extended to handle other types of events
     */
    public static class DepartmentMediator implements Mediator {

        List<Department> departments;

        @Override
        public void notify(Department sender, Employee employee) {
            for (Department department : departments) {
                if (department != sender) {
                    System.out.println("Notifying " + department.toString());
                    department.notified(employee);
                }
            }
        }

        public void setDepartments(List<Department> departments) {
            this.departments = departments;
        }
    }

    /**
     * Mediator interface
     * Mediator may have other notify(Event) methods to handle
     * types of events other than Employee, e.g. Order.
     */
    public interface Mediator {
        void notify(Department sender, Employee employee);
    }

    public static void main(String[] args) {
        DepartmentMediator mediator = new DepartmentMediator();
        HRDepartment hr = new HRDepartment(mediator);
        ITDepartment it = new ITDepartment(mediator);
        WHDepartment wh = new WHDepartment(mediator);

        mediator.setDepartments(Arrays.asList((Department)hr, (Department)wh, (Department)it));
        hr.hire(new Employee("Tom"));
    }
}