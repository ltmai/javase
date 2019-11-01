class Person {
    private String firstName;
    private String lastName;
    private String job;
    private int    age;

    public Person(String firstName, String lastName, String job, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.job = job;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void print() {
        System.out.println(job + ": " + firstName + " " + lastName + ", age: " + age);
    }
}
