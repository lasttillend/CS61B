public class Arrays {

    /** Inserts an int item into an array at the given position.
        If position is past the end of the array, insert item at the end of the array.
        @return the resulting array
      */
     public static int[] insert(int[] arr, int item, int position) {
        int[] a = new int[arr.length + 1];
        if (position >= arr.length) {
            System.arraycopy(arr, 0, a, 0, arr.length);
            a[arr.length] = item;
        } else {
            a[position] = item;
            System.arraycopy(arr, 0, a, 0, position);
            System.arraycopy(arr, position, a, position + 1, arr.length - position);
        }
        return a;
     }

     /** Reverses the items in arr. (destructively) */
     public static void reverse(int[] arr) {
        int front = 0;
        int back = arr.length - 1;
        while (front < back) {
            int temp = arr[front];
            arr[front] = arr[back];
            arr[back] = temp;
            front += 1;
            back -= 1;
        }
     }

     /** Replaces the number at index i with arr[i] copies of itself. (non-destructively)
         For example, replicate([3, 2, 1]) ---> [3, 3, 3, 2, 2, 1]
       */
    public static int[] replicate(int[] arr) {
        int length = 0;
        for (int item : arr) {
            length += item;
        }
        int[] a = new int[length];
        int aggSum = 0;
        for (int item : arr) {
            for (int cnt = 0; cnt < item; cnt++) {
                a[aggSum + cnt] = item;
            }
            aggSum += item;
        }
        return a;
    }

    public static void main(String[] args) {
        int[] a1 = new int[]{5, 9, 14, 15};
        insert(a1, 6, 2);
        reverse(a1);
        int[] a2 = {3, 2, 1};
        replicate(a2);
    }
}
