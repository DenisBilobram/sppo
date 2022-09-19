public class Program1 {
	public static double getRandomNum(int a, int b) {
		double RandomNum = (Math.random()*9)-6;
		if (RandomNum > 0.0001) {
			RandomNum += 0.0001;
			if (RandomNum > b) {
				RandomNum = b;
			}
		}
		return RandomNum;
	}
        public static void main(String[] args) {
                int[] h;
                h = new int[16];
                for (int i = 0; i < 16; i++) {
                        h[i] = i+2;
                }
                float[] x;
                x = new float[19];
		for (int i = 0; i < 19; i++) {
			float randomDouble = (float)getRandomNum(-6,3);
			x[i] = randomDouble;
		}
                double[][] a;
                a = new double[16][19];
                for (int i = 0; i < 16; i++) {
                        for (int j = 0; j < 19; j++) {
                                if (h[i] == 17) {
                                        a[i][j] = Math.exp(Math.asin(Math.cos(x[j])));
                                } else if (h[i] == 2 || h[i] == 3 || h[i] == 5 || h[i] == 6 || h[i] == 8 || h[i] == 10 ||
                                                h[i] == 13 || h[i] == 16) {
                                        a[i][j] = Math.cos((Math.pow(Math.PI*Math.sin(x[j]), 2)));
                                } else {
                                        double powNum = (Math.asin((x[j]-1.5)/9)-1)*x[j];
                                        float result = (float)(Math.pow(Math.exp(powNum)/4, 2)-1)/3;
                                        a[i][j] = result;
                                }
				System.out.printf("%10.4f", a[i][j]);
                        }
			System.out.printf("\n");
                }
	}
}
