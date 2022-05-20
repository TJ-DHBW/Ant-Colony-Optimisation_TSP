package utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Permutations implements Iterable<ArrayList<Integer>> {
    private final ArrayList<Integer> list;

    public Permutations(List<Integer> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public Iterator<ArrayList<Integer>> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            private Iterator<ArrayList<Integer>> iterator = null;

            @Override
            public boolean hasNext() {
                /*
                 * Has a next element, if:
                 * 1. There is a next element to choose.
                 * 2. There are more permutations that can be produced with the current element.
                 * 3. There is only one element which has not yet been chosen.
                 * */
                return currentIndex + 1 < list.size() || (iterator != null && iterator.hasNext()) || currentIndex == 0;
            }

            @Override
            public ArrayList<Integer> next() {
                if (list.size() <= 1) {
                    currentIndex = 1;
                    return list;
                }

                if (iterator == null) {
                    int currentElement = list.get(currentIndex);
                    iterator = new Permutations(list.stream().filter(e -> e != currentElement).toList()).iterator();
                }
                if (!iterator.hasNext()) {
                    int currentElement = list.get(++currentIndex);
                    iterator = new Permutations(list.stream().filter(e -> e != currentElement).toList()).iterator();
                }

                int currentElement = list.get(currentIndex);
                ArrayList<Integer> ret = new ArrayList<>(list.size());
                ret.add(currentElement);
                ret.addAll(iterator.next());
                return ret;
            }
        };
    }
}
