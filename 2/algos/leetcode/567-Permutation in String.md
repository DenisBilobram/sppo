Две строки, s1 и s2, нужно узнать если такая подстрака в s2, которая является перестановкой s1.

Чтобы решить за O(n), нужно двигаться окном по s2, а так же завести переменную equalsCount, которая хранит в себе кол-во символом, количество которых в s1 и в двигающемся окне по s2, совпадает. Тогда нужно просто обрабатывать кол-во символа которых выходит из окна, и кол-во символа который входит в окно, и менять equalsCount. Если equalsCount == 128 то все кол-во всех символов совпадает, и мы нашли нужную подстроку.

``` Java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        
        if (s1.length() > s2.length()) return false;

        int[] alph1 = new int[128];
        int[] alph2 = new int[128];

        int n = s1.length();
        int m = s2.length();

        for (int i = 0; i < n; i++) {
            alph1[s1.charAt(i)]++;
            alph2[s2.charAt(i)]++;
        }
        int eqls = 0;
        for (int i = 0; i < 128; i++) {
            if (alph1[i] == alph2[i]) {
                eqls++;
            }
        }

        if (eqls == 128) return true;

        char next;
        char prev;
        for (int i = 0; i < m - n; i++) {
            next = s2.charAt(n + i);
            prev = s2.charAt(i);
            
            if (alph1[next] == alph2[next]) eqls--;
            alph2[next]++;
            if (alph1[next] == alph2[next]) eqls++;

            if (alph1[prev] == alph2[prev]) eqls--;
            alph2[prev]--;
            if (alph1[prev] == alph2[prev]) eqls++;
            
            if (eqls == 128) return true;
        }

        return false;

    }
}
```