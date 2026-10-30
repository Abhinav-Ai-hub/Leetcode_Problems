class Solution {

    private int recur(String tiles, List<Character> ds, boolean[] freq) {

        int count = 0;

        for(int i = 0; i < tiles.length(); i++) {

            if(!freq[i]) {

                if(i > 0 && tiles.charAt(i) == tiles.charAt(i-1)
                        && !freq[i-1]) {
                    continue;
                }

                freq[i] = true;
                ds.add(tiles.charAt(i));

                count++;

                count += recur(tiles, ds, freq);

                ds.remove(ds.size() - 1);
                freq[i] = false;
            }
        }

        return count;
    }

    public int numTilePossibilities(String tiles) {

        char[] arr = tiles.toCharArray();
        Arrays.sort(arr);

        List<Character> ds = new ArrayList<>();
        boolean[] freq = new boolean[arr.length];

        return recur(new String(arr), ds, freq);
    }
}