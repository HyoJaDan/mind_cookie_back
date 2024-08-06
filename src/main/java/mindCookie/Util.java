package mindCookie;

public class Util {
    public static byte validateRate(byte rate) {
        if (rate < 0)
            return 0;
        else if (rate > 100)
            return 100;
        return rate;
    }
}
