package team.gif.lib;

public enum autoMode {

    MOBILITY(0),
    MOBILITY_FWD(0),
    SAFE_3_BALL(0),
    SAFE_6_BALL(0),
    SAFE_8_BALL(0),
    OPP_5_BALL(0),
    BARREL_RACING(0),
    SLALOM (0),
    BOUNCE(0);

    private int value;
    autoMode(int value) {
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
}
