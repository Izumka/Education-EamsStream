package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.*;

public class AsIntStream implements IntStream {
    private final List<Integer> collection = new ArrayList<>();

    private  AsIntStream(int... array) {
        isNull(array);
        arrayToColection(array);
    }

    private void arrayToColection(int... array){
        for (int num: array) collection.add(num);
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }
    private <T> void isNull(T values){
            if (values == null) throw new NullPointerException();
    }

    private void isEmpty(){
        if(collection.isEmpty()) throw new IllegalArgumentException("Empty stream");
    }
    @Override
    public Double average() {
        isEmpty();
        return (double)(sum() / count());
    }

    @Override
    public Integer max() {
        isEmpty();
        Integer max = Integer.MIN_VALUE;
        for (Integer num:collection) if (num > max) max = num;
        return max;

    }

    @Override
    public Integer min() {
        isEmpty();
        Integer min = Integer.MAX_VALUE;
        for (Integer num:collection) if (num < min) min = num;
        return min;
    }

    @Override
    public long count() {
        return collection.size();
    }

    @Override
    public Integer sum() {
        isEmpty();
        Integer sum = 0;
        for (Integer num: collection) sum+= num;
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        for (int i = 0; i < count(); i++) if(!predicate.test(collection.get(i)))
            collection.remove(collection.get(i));
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        for (Integer integer : collection) action.accept(integer);
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        for (int i = 0; i < count(); i++) collection.set(i, mapper.apply(collection.get(i)));
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        ArrayList<IntStream> newCollection = new ArrayList<>();
        for (Integer integer: this.collection) newCollection.add(func.applyAsIntStream(integer));
        this.collection.clear();
        for (IntStream stream: newCollection){
            for (Integer integer : stream.toArray()){
                this.collection.add(integer);
            }
        }
        return this;


    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        for (Integer integer :collection) identity = op.apply(identity, integer);
        return identity;
    }

    @Override
    public int[] toArray() {
        int[] array = new  int[(int) count()];
        for (int i = 0; i < count(); i++) array[i] = collection.get(i);
        return array;
    }

}
