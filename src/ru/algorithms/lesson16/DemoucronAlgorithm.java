package ru.algorithms.lesson16;

import ru.algorithms.lesson02.BArray;

import static java.util.Arrays.fill;

public class DemoucronAlgorithm {

    /**
     * Граф на скриншоте (нумерация с 0, а не с 1)
     */
    private static final Integer[][] graph = new Integer[][]{
            {   2,   12, null, null},
            {  12, null, null, null},
            {null, null, null, null},
            {   2, null, null, null},
            {   2,    8,    9, null},
            {   3,   10,   11, null},
            {  10, null, null, null},
            {   1,    3,    5,    6},
            {   0,   13, null, null},
            {   0,    6,   11, null},
            {   2, null, null, null},
            {null, null, null, null},
            {   2, null, null, null},
            {   5, null, null, null}
    };

    public static void main(String[] args) {
        BArray<BArray<Integer>> result;
        if ((result = topological_sort(graph)) == null) {
            System.out.println("Граф топологически несортируемый");
        } else {
            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i).toString());
            }
        }
    }

    static BArray<BArray<Integer>> topological_sort(Integer[][] graph) {

        // Результат по уровням, содержащим вершины
        BArray<BArray<Integer>> result = new BArray<>(1);

        // Список вершин с нулевой полустепенью входа
        BArray<Integer> ZERO = new BArray<>(1);

        // Список вершин исходного графа
        BArray<Integer> V = new BArray<>(graph.length);

        // Уровень (0,1,2...)
        int level = 0;

        // Матрица расчета Демукрона
        Integer[] M = new Integer[graph.length];
        fill(M, 0);

        // Первоначальный подсчет полустепеней входа всех вершин
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                Integer v = graph[i][j];
                if (v != null) {
                    M[v]++;
                }
            }
            V.add(i);
        }

        // Пока список вершин не пуст - удаляем вершины с нулевой полустепенью входа, декрементим полустепень
        // входа инцидентных вершин нулевым вершинам
        while (V.size() != 0) {
            ZERO.clear();

            // Поиск вершин с нулевой полустепенью входа
            for (int i = 0; i < M.length; i++) {
                if (M[i] != null && M[i] == 0) {
                    ZERO.add(i);
                }
            }

            /* Если нет ни одной нулевой вершины - граф топологически несортируемый */
            if (ZERO.size() == 0) {
                return null;
            }

            /* Перерасчет */
            for (int z = 0; z < ZERO.size(); z++) {
                Integer zv = ZERO.get(z);

                /* Уменьшение счетчика для ненулевых вершин */
                for (int j = 0; j < graph[zv].length; j++) {
                    Integer v = graph[zv][j];
                    if (v != null && M[v] != null) {
                        M[v]--;
                    }
                }

                /* Обнуление нулевой вершины */
                M[zv] = null;

                /* Удаление из списка вершин */
                V.remove(zv);

                /* Добавление нулевой вершины в результат по уровню */
                add_v_to_result(result, level, zv);
            }
            level++;
        }
        return result;
    }

    static void add_v_to_result(BArray<BArray<Integer>> result, int level, int v) {
        if (result.size() == level) {
            result.add(level, new BArray<>(1));
        }
        result.get(level).add(v);
    }
}
