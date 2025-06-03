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

      P11(-1.957,  -0.9263457109, -0.3766744269,   2.4592742296,   0.4066240308, -2.6548125615,  -1.0795105847,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),

      P12(-2.060,  -0.8827073508, -0.4699231137,   1.8784080311,   0.5323656966, -2.1280076906,  -1.1328782966,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),

      P13( 2.449,   0.6385346144, -0.7695931043,  -0.8297041785,  -1.2052488417,  -1.2993879420,   1.5660858119,   0.8956797780,  1.2921927739,  0.8152828684,  0.3889887851,  Double.NaN,  false ),

      P14( 2.612,   0.5051818379, -0.8630129261,  -0.5853699552,  -1.7083213634,  -1.1587311959,   1.9794852565,   0.9601162114,  1.3851548969,  0.8739354377,  0.4169731726,  Double.NaN,  false ),

      P15( 0.458,   0.4421551153,  0.8969386010,   0.4929602927,   2.0285609508, 1.1149035161,   2.2616497366,  -0.7808860949, -1.1265804966, -0.7107931551, -0.3391345220, Double.NaN,  false ),

      P16( 0.630,   0.5891447579,  0.8080275083,   0.7291147292,   1.3715262631,   1.2375816290,   1.6973757069,  -0.4620354596, -0.6665762663, -0.4205627994, -0.2006594505, Double.NaN,  false ),

      P17( 0.660,   0.6131168520,  0.7899922315,   0.7761049128,   1.2884855945,   1.2658352325,   1.6310104620,  -0.4155154440, -0.5994620704, -0.3782184564, -0.1804560645, Double.NaN,  false ),

      P18( 0.923,   0.7974154976,  0.6034306292,   1.3214700398,   0.7567330093,  1.6571913185,   1.2540513735,  -0.0801260445, -0.1155974470, -0.0729338688, -0.0347982990, Double.NaN,  false ),

      P19( 1.102,   0.8921127693,  0.4518128007,   1.9745185792,   0.5064525655, 2.2133060384,   1.1209345213,   0.0971267107,  0.1401242239,  0.0884085421,  0.0421815945,  Double.NaN,  false ),

      P20( 1.005,   0.8441619667,  0.5360882147,   1.5746698837,   0.6350537407, 1.8653646407,   1.1846067928,   0.0049875415,  0.0071955014,  0.0045398559,  0.0021660618,  Double.NaN,  false ),

      P21( 1.258,   0.9514768040,  0.3077204762,   3.0920165466,   0.3234135345,3.2497024978,   1.0509977708,   0.2295231583,  0.3311319222,  0.2089209821,  0.0996806411,  Double.NaN,  false ),

      P22(-2.462,  -0.6284762304, -0.234234,   0.8079878732,   1.123123, -1.2856299636,  -1.5911500732,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),

      P23(-2.500,  -0.5984721441, -0.34563456,   0.23424,   1.234234, -1.2482156515,  -1.6709215456,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),

      P24(-2.439,  -0.6461984907, -0.23423424,   0.435345,   1.123123, -1.3103250986,  -1.5475121258,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),
      
      P25(0.672,  0.622552, -0.34566754,   0.234346546,   1.23434234, -1.3103250986,  -1.5467657,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),
      
      P26(0.658,  0.6115356, -0.2342354,   0.456456,   1.435345345, -1.3103250986,  -1.546456,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),
      
      P27(0.659,  0.6123265, -0.4367567,   0.234243,   1.3453452, -1.3103250986,  -1.546456567,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),
      
      P28(0.598,  0.562990, -0.234234456,   0.756756756,   1.12256435, -1.3103250986,  -1.234234456,  Double.NaN, Double.NaN, Double.NaN, Double.NaN,  Double.NaN,  false ),

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





