package ru.algorithms.lesson17;

/**
 * Ребро графа
 */
public class Edge implements Comparable<Edge> {
    public int u, v, w;

    private Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Edge edge) {
        if (w != edge.w) {
            return w < edge.w ? -1 : 1;
        }
        return 0;
    }

    public static Edge of(int u, int v, int w) {
        return new Edge(u, v, w);
    }

    @Override
    public String toString() {
        return "u=" + u + ";v=" + v + ";w=" + w;
    }
}
