package lab2.common.stub;

import java.util.Arrays;
import java.util.stream.Stream;

public enum TestPoint {
    P1( -2.2,  -0.8084964038, -0.5885011173,  1.3738230568, 0.7278957760, -1.6992321181, -1.2368638812, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 16.9119068901,  true ),
    P2( -2.12,  -0.8529405,  -0.5220082,   1.6339600,   0.6120101,  -1.9156788,  -1.1724147,         Double.NaN,        Double.NaN,        Double.NaN,        Double.NaN,   7.0339190995, true),
    P3( -2.0,   -0.9092974,  -0.4161468,   2.1850399,   0.4576576,  -2.4029980,  -1.0997502,         Double.NaN,        Double.NaN,        Double.NaN,        Double.NaN,   4.1593776956, true),
    P4( -1.83,  -0.9665944,  -0.2563109,   3.7711793,   0.2651690,  -3.9015117,  -1.0345601,         Double.NaN,        Double.NaN,        Double.NaN,        Double.NaN,   5.1050657493, true),
    P5( -0.87,  -0.7643289,   0.6448265,  -1.1853249,  -0.8436506,   1.5508046,  -1.3083372,         Double.NaN,        Double.NaN,        Double.NaN,        Double.NaN,   4.4122510384, true),
    P6( -0.37,  -0.3616154,   0.9323273,  -0.3878632,  -2.5782289,   1.0725847,  -2.7653687,         Double.NaN,        Double.NaN,        Double.NaN,        Double.NaN,   2297.1898528451, true),
    P7(  0.8,    0.7173561,   0.6967067,   1.0296386,   0.9712146,   1.4353242,   1.3940078,  -0.22314355, -0.32192809, -0.2031140136, -0.0969100130,   0.0119116483, true),
    P8(  2.33,   0.7253844,  -0.6883440,  -1.0538108,  -0.9489369,  -1.4527619,   1.3785794,   0.84586827,  1.22032995,  0.7699424777,  0.3673559210,   0.9825747121, true),
    P9(  2.53,   0.5741721,  -0.8187346,  -0.7012922,  -1.4259392,  -1.2213970,   1.7416379,   0.92821930,  1.33913738,  0.8449016203,  0.4031205212,   1.2584789033, true),
    P10( 2.67,   0.4543057,  -0.8908459,  -0.5099711,  -1.9608953,  -1.1225286,   2.2011612,   0.98207847,  1.41683974,  0.8939263492,  0.4265112614,   1.4638672378, true),

    S_P1(3.7707963268, -0.5885011173, -0.8084964038, 0.7278957760, 1.3738230568,
          -1.2368638812, -1.6992321181,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          Double.NaN, false),
    S_P2(  3.690796327,  -0.522008175, -0.852940482,  0.612010083,  1.633960007,
          -1.172414748, -1.915678810,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          Double.NaN, false ),

    S_P3(  3.570796327,  -0.416146837, -0.909297427,  0.457657554,  2.185039863,
          -1.099750170, -2.402997962,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          Double.NaN, false ),

    S_P4(  3.400796327,  -0.256310908, -0.966594392,  0.265169041,  3.771179301,
          -1.034560110, -3.901511672,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          Double.NaN, false ),

    S_P5(  2.440796327,   0.644826547, -0.764328937, -0.843650575, -1.185324860,
          -1.308337224,  1.550804638,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          Double.NaN, false ),

    S_P6(  1.940796327,   0.932327346, -0.361615432, -2.578228867, -0.387863162,
          -2.765368708,  1.072584650,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          Double.NaN, false ),

    L2 (  2.0,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          0.6931471806, 1.0000000000, 0.6309297536, 0.3010299957,
          Double.NaN, false ),

    L3 (  3.0,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          1.0986122887, 1.5849625007, 1.0000000000, 0.4771212547,
          Double.NaN, false ),

    L10( 10.0,
          Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN,
          2.3025850930, 3.3219280949, 2.0959032743, 1.0000000000,
          Double.NaN, false );

    public final double x;
    public final double sin, cos, tan, cot, sec, csc;
    public final double ln, log2, log3, log10;
    public final double systemValue;
    private final boolean primary;

    TestPoint(double x,
              double sin, double cos, double tan, double cot, double sec, double csc,
              double ln, double log2, double log3, double log10,
              double systemValue, boolean primary) {
        this.x = x;
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.cot = cot;
        this.sec = sec;
        this.csc = csc;
        this.ln = ln;
        this.log2 = log2;
        this.log3 = log3;
        this.log10 = log10;
        this.systemValue = systemValue;
        this.primary = primary;
    }

    public static TestPoint forX(double x) {
        for (TestPoint tp : values()) {
            if (Math.abs(tp.x - x) < 1e-9) {
                return tp;
            }
        }
        throw new IllegalArgumentException("No table data for x = " + x);
    }

    public static Stream<TestPoint> primaryStream() {
        return Arrays.stream(values())
                     .filter(tp -> tp.primary);
    }
}





