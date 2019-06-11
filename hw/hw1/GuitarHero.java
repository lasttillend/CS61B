/** Replicate a guitar string sound with the support of a total of 37 notes
 * on the chromatic scale from 110 Hz to 880Hz.
 */
import edu.princeton.cs.algs4.StdAudio;
import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {

    /*  using 37 keys to represent the KEYBOARD, from lowest note to highest note */
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        GuitarString[] strings = new GuitarString[KEYBOARD.length()];

        for (int i = 0; i < KEYBOARD.length(); i++) {
            /* The ith character of the string KEYBOARD corresponds to a frequency of
            440 * 2^((i - 24) / 12) */
            strings[i] = new GuitarString(440 * Math.pow(2, (i - 24) / 12));
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index >= 0 && index < 37) {
                    strings[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i = 0; i < KEYBOARD.length(); i++) {
                sample += strings[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < KEYBOARD.length(); i++) {
                strings[i].tic();
            }
        }
    }
}
