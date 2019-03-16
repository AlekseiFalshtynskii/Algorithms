package ru.algorithms.lesson15;

import ru.algorithms.lesson02.BArray;

import java.util.Arrays;

import static java.util.Arrays.fill;

public class KosarajuAlgorithm {

    /**
     * Граф на скриншоте (буквы заменены числами 0..7)
     */
    private static final Integer[][] graph = new Integer[][]{
            {1, null, null},
            {2,    4,    5},
            {3, null, null},
            {2,    7, null},
            {0,    5, null},
            {6, null, null},
            {5, null, null},
            {3,    6, null},
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
    static Integer[] searchStronglyConnectedComponents(Integer[][] graph) {
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
        Integer[][] reversedGraph = reverseGraph(graph);

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

    static Integer[][] reverseGraph(Integer[][] graph) {
        Integer[][] reversed = new Integer[graph.length][graph[0].length];
        for (Integer[] r : reversed) {
            fill(r, null);
        }
        int[] indexes = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                Integer v = graph[i][j];
                if (v == null) {
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
    static void dfs(int v, boolean[] visited, Integer[][] graph, BArray<Integer> order) {
        visited[v] = true;
        for (int j = 0; j < graph[v].length; j++) {
            if (graph[v][j] != null && !visited[graph[v][j]]) {
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
    static void dfsReversed(int v, boolean[] visited, Integer[][] reversedGraph, BArray<Integer> components, int component_index) {
        visited[v] = true;
        components.add(v, component_index);
        System.out.print(v + " ");
        for (int j = 0; j < reversedGraph[v].length; j++) {
            if (reversedGraph[v][j] != null && !visited[reversedGraph[v][j]]) {
                dfsReversed(reversedGraph[v][j], visited, reversedGraph, components, component_index);
            }
        }
    }
}
