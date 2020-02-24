package team.gif.lib;

public enum delay {

    DELAY_0(15),
    DELAY_1(14),
    DELAY_2(13),
    DELAY_3(12),
    DELAY_4(11),
    DELAY_5(10);

    private double value;
    delay(double value) {
        this.value = value;
    }
    public static double getvalue(delay d){
        return d.value;
    }
}
