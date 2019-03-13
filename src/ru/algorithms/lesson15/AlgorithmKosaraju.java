package ru.algorithms.lesson15;

import ru.algorithms.lesson02.BArray;

import java.util.Arrays;

import static java.util.Arrays.fill;

public class AlgorithmKosaraju {

    /**
     * Граф на скриншоте (буквы заменены числами 0..7)
     */
    private static final int[][] graph = new int[][]{
            {1, -1, -1},
            {2, 4, 5},
            {3, -1, -1},
            {2, 7, -1},
            {0, 5, -1},
            {6, -1, -1},
            {5, -1, -1},
            {3, 6, -1},
    };

    public static void main(String[] args) {
        System.out.println("\nРезультат:\n" + Arrays.toString(searchStronglyConnectedComponents(graph)));
    }

    /**
     * Поиск компонент сильной связности
     *
     * @param graph - матрица смежности
     * @return
     */
    static Integer[] searchStronglyConnectedComponents(int[][] graph) {
        boolean[] visited = new boolean[graph.length];

        BArray<Integer> order = new BArray<>(graph.length);

        for (int i = 0; i < graph.length; i++) {
            if (!visited[i]) {
                dfs(i, visited, graph, order);
            }
        }

        BArray<Integer> components = new BArray<>(graph.length);
        int component_index = 1;
        fill(visited, false);

        /* Реверсивный граф */
        int[][] reversedGraph = reverseGraph(graph);

        while (order.size() != 0) {
            int v = order.remove(order.size() - 1);
            if (!visited[v]) {
                System.out.print("Компонент связности " + component_index + ": ");
                dfsReversed(v, visited, reversedGraph, components, component_index);
                component_index++;
                System.out.println();
            }
        }
        return components.toArray(new Integer[]{});
    }

    static int[][] reverseGraph(int[][] graph) {
        int[][] reversed = new int[graph.length][graph[0].length];
        for (int[] r : reversed) {
            fill(r, -1);
        }
        int[] indexes = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                int v = graph[i][j];
                if (v == -1) {
                    continue;
                }
                reversed[v][indexes[v]++] = i;
            }
        }
        return reversed;
    }

    /**
     * Прямой обход в глубину
     * @param v
     * @param visited
     * @param graph
     * @param order
     */
    static void dfs(int v, boolean[] visited, int[][] graph, BArray<Integer> order) {
        visited[v] = true;
        for (int j = 0; j < graph[v].length; j++) {
            if (graph[v][j] != -1 && !visited[graph[v][j]]) {
                dfs(graph[v][j], visited, graph, order);
            }
        }
        order.add(v);
    }

    /**
     * Реверсивный обход в глубину
     * @param v
     * @param visited
     * @param reversedGraph
     * @param components
     * @param component_index
     */
    static void dfsReversed(int v, boolean[] visited, int[][] reversedGraph, BArray<Integer> components, int component_index) {
        visited[v] = true;
        components.add(v, component_index);
        System.out.print(v + " ");
        for (int j = 0; j < reversedGraph[v].length; j++) {
            if (reversedGraph[v][j] != -1 && !visited[reversedGraph[v][j]]) {
                dfsReversed(reversedGraph[v][j], visited, reversedGraph, components, component_index);
            }
        }
    }
}
