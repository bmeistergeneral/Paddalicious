public class Swap {

    int[] someNumbers = { 1, 2, 3, 4, 5, 6, 7, 8};

    // swap the 4th and seventh items in the array
    public void swap(int indexOfFirstItem, int indexOfSecondItem) {
        int tempA = someNumbers[indexOfFirstItem];
        someNumbers[indexOfFirstItem] = someNumbers[indexOfSecondItem];
        someNumbers[indexOfSecondItem] = tempA;
    }

    public void printValues() {
        System.out.print("Values: ");
        for (int i=0; i< someNumbers.length; i++) {
            System.out.print(someNumbers[i] + " ");
        }
        System.out.print("\n");
    }

    public static void main(String[] args) {

        Swap swap = new Swap();

        swap.printValues();
        swap.swap(3, 6);
        swap.printValues();
        swap.swap(0, 1);
        swap.printValues();
    }
}
