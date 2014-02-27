package crossover;

import clojure.lang.PersistentArrayMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;

import java.util.*;

public class Order1 {

    private static Random _random = new Random();

    private static int nextInt(int max) {
        return _random.nextInt(max);
    }

    private static int randIntInRange(int min, int max) {
        return _random.nextInt(max - min) + min;
    }

    private enum GenesType {
        PersistentHashMap, PersistentArrayMap
    }

    private static GenesType getChromType(Class genesClass) {
        if (genesClass == null) return null;

        if (genesClass.equals(PersistentHashMap.class)) {
            return GenesType.PersistentHashMap;
        }
        if (genesClass.equals(PersistentArrayMap.class)) {
            return GenesType.PersistentArrayMap;
        }
        return null;
    }

    private static <T> PersistentVector operate(List<T> genes_X, List<T> genes_Y) {
        int numberSwappingElements = randIntInRange(2, genes_X.size());
        int randomStartPoint = nextInt(genes_X.size() - numberSwappingElements);

        T nextGene;
        int index, next;
        for (index = randomStartPoint; index < randomStartPoint + numberSwappingElements; index++) {
            genes_Y.set(genes_Y.indexOf(genes_X.get(index)), null);
        }
        for (index = 0; index < randomStartPoint; index++) {
            if (genes_Y.get(index) != null) continue;
            next = index + 1;
            while (true) {
                nextGene = genes_Y.get(next);
                if (nextGene == null) {
                    next++;
                    continue;
                }
                genes_Y.set(index, nextGene);
                genes_Y.set(next, null);
                break;
            }
        }
        for (index = genes_Y.size() - 1; index > randomStartPoint + numberSwappingElements - 1; index--) {
            if (genes_Y.get(index) != null) continue;
            next = index - 1;
            while (true) {
                nextGene = genes_Y.get(next);
                if (nextGene == null) {
                    next--;
                    continue;
                }
                genes_Y.set(index, nextGene);
                genes_Y.set(next, null);
                break;
            }
        }

        for (index = 0; index < genes_Y.size(); index++) {
            if (genes_Y.get(index) != null) continue;
            genes_Y.set(index, genes_X.get(index));
        }

        return PersistentVector.create(genes_Y);
    }

    public static PersistentVector operate(PersistentVector chrom_X, PersistentVector chrom_Y) {
        if (chrom_X == null || chrom_Y == null) return null;
        if (chrom_X.size() == 0 || chrom_Y.size() == 0) return null;

        GenesType genesType = getChromType(chrom_X.get(0).getClass());

        if (genesType == GenesType.PersistentHashMap) {
            List<PersistentHashMap> genes_X = new ArrayList<PersistentHashMap>();
            List<PersistentHashMap> genes_Y = new ArrayList<PersistentHashMap>();

            for (Object aChrom_X : chrom_X) {
                genes_X.add((PersistentHashMap) aChrom_X);
            }
            for (Object aChrom_Y : chrom_Y) {
                genes_Y.add((PersistentHashMap) aChrom_Y);
            }
            return operate(genes_X, genes_Y);
        }
        if (genesType == GenesType.PersistentArrayMap) {
            List<PersistentArrayMap> genes_X = new ArrayList<PersistentArrayMap>();
            List<PersistentArrayMap> genes_Y = new ArrayList<PersistentArrayMap>();

            for (Object aChrom_X : chrom_X) {
                genes_X.add((PersistentArrayMap) aChrom_X);
            }
            for (Object aChrom_Y : chrom_Y) {
                genes_Y.add((PersistentArrayMap) aChrom_Y);
            }
            return operate(genes_X, genes_Y);
        }
        return null;
    }
}
