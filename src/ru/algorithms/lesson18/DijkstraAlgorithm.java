package ru.algorithms.lesson18;

import ru.algorithms.lesson02.BArray;
import ru.algorithms.lesson17.Edge;

import static java.util.Arrays.fill;
import static ru.algorithms.lesson17.Edge.of;

public class DijkstraAlgorithm {

    /**
     * Граф на скриншоте (буквы заменены числами 0..7)
     */
    private static final int[][] graph = new int[][]{
            {0,  7,  9,  0, 0, 14},
            {7,  0, 10, 15, 0,  0},
            {9, 10,  0, 11, 0,  2},
            {0, 15, 11,  0, 6,  0},
            {0,  0,  0,  6, 0,  9},
            {14, 0,  2,  0, 9,  0},
    };

    public static void main(String[] args) throws CloneNotSupportedException {

        // Вывод всех путей до всех вершин от каждой вершины
        for (int v = 0; v < graph.length; v++) {
            System.out.println(shortest_paths(graph, v));
        }
    }

    /**
     * Поиск минимальных путей от заданной до вершины до всех остальных
     *
     * @param graph
     * @param v_start
     * @return
     * @throws CloneNotSupportedException
     */
    static BArray<BArray<Edge>> shortest_paths(int[][] graph, int v_start) throws CloneNotSupportedException {
        Integer[] dist = new Integer[graph.length];
        Integer[][] prev = new Integer[graph.length][graph.length];
        fill(dist, Integer.MAX_VALUE);
        dist[v_start] = 0;

        while (!isEmptyDist(dist)) {
            int v = min(dist);
            for (int j = 0; j < graph[v].length; j++) {
                int w = graph[v][j];
                if (dist[j] == null || w == 0) {
                    continue;
                }
                if (dist[v] + w < dist[j]) {
                    dist[j] = dist[v] + w;
                    emptyPrevColumn(prev, j);
                    prev[v][j] = dist[j];
                }
            }
            dist[v] = null;
        }
        return recovery_paths(prev, v_start);
    }

    /**
     * Проверка, что все вершины обработаны
     *
     * @param dist
     * @return
     */
    static boolean isEmptyDist(Integer[] dist) {
        for (Integer d : dist) {
            if (d != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Обнуление длины минимального пути до вершины из любой другой вершины
     *
     * @param prev
     * @param v
     */
    static void emptyPrevColumn(Integer[][] prev, int v) {
        for (int i = 0; i < prev.length; i++) {
            if (prev[i][v] != null) {
                prev[i][v] = null;
                return;
            }
        }
    }

    /**
     * Поиск вершины с минимальным путем достижения
     *
     * @param dist
     * @return
     */
    static int min(Integer[] dist) {
        int v = 0;
        int min_w = Integer.MAX_VALUE;
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] != null && dist[i] < min_w) {
                min_w = dist[i];
                v = i;
            }
        }
        return v;
    }

    /**
     * Восстановление путей
     *
     * @param prev
     * @param v - вершина, от которой ищутся пути
     * @return
     * @throws CloneNotSupportedException
     */
    static BArray<BArray<Edge>> recovery_paths(Integer[][] prev, int v) throws CloneNotSupportedException {
        BArray<BArray<Edge>> result = new BArray<>(1);
        for (int i = v; i < prev.length; i++) {
            for (int j = 0; j < prev.length; j++) {
                if (prev[i][j] != null) {
                    add_edge_to_result(result, of(i, j, graph[i][j]));
                }
            }
        }
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < prev.length; j++) {
                if (prev[i][j] != null) {
                    add_edge_to_result(result, of(i, j, graph[i][j]));
                }
            }
        }
        return result;
    }

    /**
     * Добавление ребра в результат путей (путь, совпадающий по нескольим вершинам копируется, чтобы все пути были отдельные)
     *
     * @param result
     * @param edge
     * @throws CloneNotSupportedException
     */
    static void add_edge_to_result(BArray<BArray<Edge>> result, Edge edge) throws CloneNotSupportedException {
        for (int i = 0; i < result.size(); i++) {
            if (lastEdge(result.get(i)).v == edge.u) {
                BArray<Edge> clone = (BArray<Edge>) result.get(i).clone();
                clone.add(edge);
                result.add(clone);
                return;
            }
        }
        result.add(new BArray<>(1));
        result.get(result.size() - 1).add(edge);
    }

    /**
     * Получение последнего ребра, к которому прицепляется новое
     *
     * @param edges
     * @return
     */
    static Edge lastEdge(BArray<Edge> edges) {
        return edges.get(edges.size() - 1);
    }
}
