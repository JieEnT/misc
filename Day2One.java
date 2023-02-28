public class Day2One {
    public int[] wordToIntArr(String word) {
        int[] ans = new int[26];
        for (char c : word.toCharArray()) {
            ans[c - 'a']++;
        }
        return ans;
    }

    public List<int[]> stringsToCharList(List<String> clippings) {
        ArrayList<int[]> store = new ArrayList<>();

        for (String clipping : clippings) {
            store.add(wordToIntArr(clipping));
        }

        return store;
    }

    public boolean isRedundantCharArrDueToNoMatching(int[] source, int[] target) {
        boolean isRedundant = true;

        for (int i = 0; i < 26; i++) {
            if (target[i] > 0 && source[i] > 0) {
                isRedundant = false;
            }
        }

        return isRedundant;
    }

    public List<int[]> isRedundantCharArrDueToBetterOption(List<int[]> listOfIntArr, int[] target) {
        ArrayList<int[]> store = new ArrayList<>();

        for (int i = 0; i < listOfIntArr.size(); i++) {
            int[] currArr = listOfIntArr.get(i);
            boolean isRedundant = false;

            for (int j = 0; j < listOfIntArr.size(); j++) {

                if (i == j)
                    continue;

                int[] toCompareArr = listOfIntArr.get(j);
                boolean hasLessUsefulChar = true;

                for (int k = 0; k < 26; k++) {
                    if (target[k] > 0 && (currArr[k] > toCompareArr[k])) {
                        hasLessUsefulChar = false;
                    }
                }

                if (hasLessUsefulChar) {
                    isRedundant = true;
                }
            }

            if (!isRedundant) {
                store.add(currArr);
            }
        }

        return store;
    }

    public List<int[]> removeUnnecessaryCharArr(List<int[]> listOfIntArr, int[] targetIntArr) {
        List<int[]> store = new ArrayList<>();

        for (int[] intArr : listOfIntArr) {
            if (!isRedundantCharArrDueToNoMatching(intArr, targetIntArr)) {
                store.add(intArr);
            }
        }

        List<int[]> storeTwo = isRedundantCharArrDueToBetterOption(store, targetIntArr);

        return storeTwo;
    }

    public void intArrToString(int[] arr) {
        for (int i = 0; i < 26; i++) {
            System.out.print(arr[i] + ":" + (char) (i + 'a') + " ");
        }
        System.out.println();
    }

    public boolean containsChar(int[] sourceIntArr, int[] targetIntArr) {
        for (int i = 0; i < 26; i++) {
            if (targetIntArr[i] > 0 && sourceIntArr[i] > 0) {
                return true;
            }
        }

        return false;
    }

    public int[] deductChar(int[] sourceIntArr, int[] targetIntArr) {
        int[] ans = new int[26];

        for (int i = 0; i < 26; i++) {
            ans[i] = Math.max(0, targetIntArr[i] - sourceIntArr[i]);
        }

        return ans;
    }

    public boolean isEmpty(int[] targetIntArr) {

        boolean empty = true;

        for (int i = 0; i < 26; i++) {

            if (targetIntArr[i] > 0) {
                empty = false;
            }

        }

        return empty;
    }

    public int getMinStickers(int currIndex, List<int[]> usefulListOfIntArr, int[] targetIntArr) {

        // If there is nothing to deduct then return 0
        if (currIndex >= usefulListOfIntArr.size()) {
            return 100_000_000;
        }

        if (isEmpty(targetIntArr)) {
            return 0;
        }

        int minNo = Integer.MAX_VALUE;
        int[] currSourceIntArr = usefulListOfIntArr.get(currIndex);

        if (containsChar(currSourceIntArr, targetIntArr)) {
            int[] newTargetIntArr = deductChar(currSourceIntArr, targetIntArr);
            minNo = Math.min(minNo, getMinStickers(currIndex, usefulListOfIntArr, newTargetIntArr) + 1);
        }

        minNo = Math.min(minNo, getMinStickers(currIndex + 1, usefulListOfIntArr, targetIntArr));

        return minNo;
    }

    public static void main(String[] args) {
        Test test = new Test();

        List<String> clippings = Arrays.asList("whale".split(" "));
        String word = "watchout";

        int[] targetIntArr = test.wordToIntArr(word);
        List<int[]> listOfIntArr = test.stringsToCharList(clippings);
        List<int[]> usefulListOfIntArr = test.removeUnnecessaryCharArr(listOfIntArr, targetIntArr);

        int ans = test.getMinStickers(0, usefulListOfIntArr, targetIntArr);
        ans = (ans == 100000000) ? -1 : ans;

        System.out.println(ans);

    }
}
