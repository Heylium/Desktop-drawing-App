import { ChemicalElements } from "../src/lib/elements";
import { BondType, EdgeShape, EdgeTopology } from "../src/view/Edge";
import { Graph } from "../src/view/Graph";

test("Draw graph programmatically and check topology", () => {
    const graph = new Graph();
    graph.add_vertex({x: 10, y: 10});
    expect(graph.vertices.length).toBe(1);
    expect(graph.edges.length).toBe(0);
    graph.add_vertex({x: 20, y: 20});
    graph.bind_vertices(graph.vertices[0], graph.vertices[1]);
    expect(graph.vertices.length).toBe(2);
    expect(graph.edges.length).toBe(1);
    graph.add_vertex({x: 30, y: 30});
    graph.bind_vertices(graph.vertices[1], graph.vertices[2]);
    expect(graph.vertices.length).toBe(3);
    expect(graph.edges.length).toBe(2);
    graph.add_vertex({x: 40, y: 40});
    graph.bind_vertices(graph.vertices[2], graph.vertices[3]);
    expect(graph.vertices.length).toBe(4);
    expect(graph.edges.length).toBe(3);
    graph.update_topology();
    expect(graph.ringsystems.length).toBe(0);
    graph.bind_vertices(graph.vertices[0], graph.vertices[3]);
    expect(graph.vertices.length).toBe(4);
    expect(graph.edges.length).toBe(4);
    graph.update_topology();
    expect(graph.ringsystems.length).toBe(1);
});

test("Read-write-read mol file and test", () => {
    const mol = `Molecule name
Generated by Butlerov
[no comment provided]
 21 21  0  0  0  0  0  0  0  0  1 V2000
    -1.6163    0.7276    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    -2.4253    0.1399    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
    -2.1163   -0.8112    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    -1.1163   -0.8112    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
    -0.8073    0.1399    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    -3.1163   -0.8112    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    -2.1163   -1.8112    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    -1.6163    1.7276    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.1437    0.4489    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    -0.7503    2.2276    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
    -2.4824    2.2276    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
    0.8869   -0.2203    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.3517    1.4270    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
    1.8379    0.0888    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.6790   -1.1984    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    1.4221   -1.8675    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    2.3732   -1.5585    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    2.5811   -0.5804    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    3.1163   -2.2276    0.0000 F   0  0  0  0  0  0  0  0  0  0  0  0
    -2.1163   -2.8112    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    -1.0600    4.1882    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0
  1  2  1  0  0  0  0
  2  3  1  0  0  0  0
  3  4  1  0  0  0  0
  4  5  1  0  0  0  0
  5  1  1  0  0  0  0
  3  6  1  0  0  0  0
  3  7  1  0  0  0  0
  1  8  1  1  0  0  0
  5  9  1  6  0  0  0
  8 10  1  0  0  0  0
  8 11  2  0  0  0  0
  9 12  1  0  0  0  0
  9 13  2  0  0  0  0
 12 14  2  0  0  0  0
 12 15  1  0  0  0  0
 15 16  2  0  0  0  0
 16 17  1  0  0  0  0
 17 18  2  0  0  0  0
 18 14  1  0  0  0  0
 17 19  1  0  0  0  0
  7 20  3  0  0  0  0
M  CHG  2  10  -1  21   1
M  END`;
    const graph = new Graph();
    graph.load_mol_string(mol);
    expect(graph.vertices.length).toBe(21);
    expect(graph.edges.length).toBe(21);
    const mol_str2 = graph.get_mol_string();
    graph.clear();
    expect(graph.vertices.length).toBe(0);
    expect(graph.edges.length).toBe(0);
    graph.load_mol_string(mol_str2);
    expect(graph.edges.filter(e => e.topology == EdgeTopology.Ring).length).toBe(11);
    expect(graph.edges.filter(e => e.topology == EdgeTopology.Chain).length).toBe(10);
    expect(graph.edges.filter(e => e.bond_type == BondType.Double).length).toBe(5);
    expect(graph.edges.filter(e => e.bond_type == BondType.Triple).length).toBe(1);
    expect(graph.edges.filter(e => e.shape == EdgeShape.SingleUp).length).toBe(1);
    expect(graph.edges.filter(e => e.shape == EdgeShape.SingleDown).length).toBe(1);
    expect(graph.vertices.filter(e => e.element == ChemicalElements["F"]).length).toBe(1);
    expect(graph.vertices.filter(e => e.element == ChemicalElements["O"]).length).toBe(5);
    expect(graph.vertices.filter(e => e.element == ChemicalElements["N"])[0].charge).toBe(1);
    expect(graph.ringsystems.length).toBe(2);
});