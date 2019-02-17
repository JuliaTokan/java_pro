package test_annotation_reflection;

public class TestSaveClass {
    @Save
    private int parameter1;

    @Save
    private String parameter2;

    @Save
    private double parameter3;

    private String parameter4;

    public TestSaveClass(int parameter1, String parameter2, double parameter3, String parameter4) {
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
        this.parameter3 = parameter3;
        this.parameter4 = parameter4;
    }

    public TestSaveClass(){}

    public int getParameter1() {
        return parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public double getParameter3() {
        return parameter3;
    }

    public String getParameter4() {
        return parameter4;
    }

    public void setParameter1(int parameter1) {
        this.parameter1 = parameter1;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    public void setParameter3(double parameter3) {
        this.parameter3 = parameter3;
    }

    public void setParameter4(String parameter4) {
        this.parameter4 = parameter4;
    }
}
