package lab1;

public class DFA {
    public static boolean accepts(String input) {
        int state = 0;
        for (char c : input.toCharArray()) {
            switch (state) {
                case 0:
                    if (c == 'a') state = 1;
                    else state = 0;
                    break;
                case 1:
                    if (c == 'a') state = 2;
                    else state = 0;
                    break;
                case 2:
                    if (c == 'a') state = 3;
                    else state = 2;
                    break;
                case 3:
                    if (c == 'a') state = 4;
                    else state = 2;
                    break;
                case 4:
                    state = 4;
                    break;
            }
        }
        return state == 4;
    }
}