package ru.algorithms.lesson17;

import ru.algorithms.lesson02.BArray;

import java.util.Arrays;

import static ru.algorithms.lesson06.QuickSort.quickSort;
import static ru.algorithms.lesson17.Edge.of;

public class KruskalAlgorithm {

    /**
     * Граф на скриншоте (буквы заменены числами 0..7)
     */
    private static final int[][] graph = new int[][]{
            {0, 7, 0,  5,  0,  0,  0},
            {7, 0, 8,  9,  7,  0,  0},
            {0, 8, 0,  0,  5,  0,  0},
            {5, 9, 0,  0, 15,  6,  0},
            {0, 7, 5, 15,  0,  8,  9},
            {0, 0, 0,  6,  8,  0, 11},
            {0, 0, 0,  0,  9, 11,  0}
    };

    public static void main(String[] args) {

        // Формрирование списка ребер Edge[] из матрицы смежности
        BArray<Edge> edgeBArray = new BArray<>(1);
        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph.length; j++) {
                if (graph[i][j] != 0) {
                    edgeBArray.add(of(i, j, graph[i][j]));
                }
            }
        }
        Edge[] edges = edgeBArray.toArray(new Edge[]{});
        System.out.println(Arrays.toString(min_spanning_tree(edges)));
    }

    static Edge[] min_spanning_tree(Edge[] edges) {
        quickSort(edges);

        // Список компонентов связности
        int[] components = new int[graph.length];
        for (int i = 0; i < components.length; i++) {
            components[i] = i;
        }
        BArray<Edge> result_edges = new BArray<>(1);
        for (Edge edge : edges) {

            // Вершины ребра не входят в один компонент смежности
            if (components[edge.u] != components[edge.v]) {
                result_edges.add(edge);

                // Корректировка компонентов смежности
                int a = components[edge.u];
                int b = components[edge.v];
                for (int i = 0; i < components.length; i++) {
                    if (components[i] == b) {
                        components[i] = a;
                    }
                }
            }
        }
        return result_edges.toArray(new Edge[]{});
    }
}
