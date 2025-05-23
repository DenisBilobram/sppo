package lab2;

import lab2.common.UnaryFunction;
import lab2.trig.*;
import lab2.log.*;

public class SystemFunction implements UnaryFunction {

    private final UnaryFunction sin, cos, tan, cot, sec, csc;
    private final UnaryFunction ln, log2, log3, log10;


    public SystemFunction() {
        this.cos = new CosFunction(1e-12);
        this.sin = new SinFunction(this.cos);
        this.tan = new TanFunction(this.sin, this.cos);
        this.cot = new CotFunction(this.sin, this.cos);
        this.sec = new SecFunction(this.cos);
        this.csc = new CscFunction(this.sin);
        this.ln = new LnFunction(1e-12);
        this.log2 = new Log2Function(this.ln);
        this.log3 = new Log3Function(this.ln);
        this.log10 = new Log10Function(this.ln);
    }

    public SystemFunction(UnaryFunction sin, UnaryFunction cos, UnaryFunction tan,
                           UnaryFunction cot, UnaryFunction sec, UnaryFunction csc,
                           UnaryFunction ln, UnaryFunction log2, UnaryFunction log3, UnaryFunction log10) {
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
    }

    @Override
    public double apply(double x) {
        if (x <= 0) {

            double sinVal = sin.apply(x);
            double cosVal = cos.apply(x);
            double tanVal = tan.apply(x);
            double cotVal = cot.apply(x);
            double secVal = sec.apply(x);
            double cscVal = csc.apply(x);

            double part1 = sinVal * cotVal;
            double part2 = Math.pow(part1, 3);
            double part3 = part2 * cosVal;
            double part4 = part3 / sinVal;
            double part5 = part4 + cotVal;
            double part6 = Math.pow(part5, 2);

            double part7 = part6 - tanVal;
            double part8 = part7 - secVal;
            double part9 = part8 - secVal;

            double numerator = cotVal;
            double inner = (cotVal * (sinVal * tanVal)) / cscVal;
            double innerDiff = inner - cscVal;
            double frac1 = numerator / innerDiff;
            double part10 = frac1 / cotVal;
            double part11 = part9 * part10;


            double part12 = part11 * cosVal;

            double inner2 = tanVal * sinVal - cscVal;
            double denom1 = sinVal * inner2;
            double frac2 = tanVal / denom1;
            double denom2 = sinVal * cotVal;
            double part13 = frac2 / denom2;
            double part14 = part12 * part13;

            double A = secVal - tanVal;
            double B = cscVal + cscVal;
            double C = A * B;
            double D = secVal + (cosVal + cotVal);
            double E = tanVal - D;
            double F = C - E;
            double G = F / secVal;
            double H = secVal / G;
            double part15 = Math.pow(H, 2);
            double part16 = part14 - part15;

            double I = secVal + tanVal;
            double J = Math.pow(I, 2);
            double K = tanVal / sinVal;
            double L = K + tanVal;
            double M = (cosVal * cosVal) / (cotVal * cotVal);
            double N = L - M;
            double O = N - (cotVal * cotVal);
            double part17 = part16 * (J / O);

            double P = sinVal * tanVal * (cscVal * cscVal);
            double innerFrac = (cosVal * cscVal) / ( (cosVal/cosVal)*(cosVal/cosVal) - secVal * sinVal );

            double Q = cscVal / innerFrac;

            double R = (tanVal - cscVal) * ((cosVal + secVal) / (cscVal - secVal));

            double S = sinVal + (R + cotVal);

            double inner3 = sinVal * (cotVal + cscVal);
            double inner4 = cscVal / (inner3 / cotVal);
            double T = S - (tanVal / inner4);

            double U = Math.pow(cotVal + Math.pow(cscVal, 3), 2);

            double bigPart = Q - (T * U);

            return part17 + P + bigPart;

        } else {

            double lnVal = ln.apply(x);
            double log2Val = log2.apply(x);
            double log3Val = log3.apply(x);
            double log10Val = log10.apply(x);
            double partA = (log2Val + lnVal) * log10Val;
            double partB = partA - log10Val;
            double partC = partB + log3Val;
            return partC * lnVal;
            
        }
    }
}

