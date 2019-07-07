public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        int n = input.length();
        int m = pattern.length();
        if (m > n) {
            return -1;
        }

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < m; i++) {
            strBuilder.append(input.charAt(i));
        }
        RollingString rollInput = new RollingString(strBuilder.toString(), m);
        RollingString rollPattern = new RollingString(pattern, m);

        int inputHash = rollInput.hashCode();
        int patternHash = rollPattern.hashCode();
        for (int s = 0; s < n - m + 1; s++) {
            if (inputHash == patternHash) {
                if (rollInput.equals(rollPattern)) {
                    return s;
                }
            }
            if (s < n - m) {
                rollInput.addChar(input.charAt(s + m));
                inputHash = rollInput.hashCode();
            }
        }
        return -1;
    }
}
