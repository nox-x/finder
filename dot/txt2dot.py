#!/usr/bin/env python3

from os import listdir

for filename in listdir('../'):
    if filename.startswith('graph'):
        with open('../{0}'.format(filename)) as input:
            with open('{0}.dot'.format(filename), 'w') as output:
                output.write('graph G {\n')
                output.write('rankdir=LR\n')
                first = True
                start_node = None
                end_node = None
                ranked = 0
                max_node = 0
                for line in input:
                    if first:
                        first = False
                        start_node, end_node = line.split()
                        continue
                    nodes = line.split()
                    from_node = nodes[0]
                    to_nodes = nodes[1:]
                    next_ranked = None
                    if len(to_nodes) > 0:
                        if ranked == int(from_node):
                            next_ranked = max(map(int, to_nodes))
                            if next_ranked == ranked + 1:
                                next_ranked = None
                    else:
                        next_ranked = max_node + 1
                    for node in to_nodes:
                        output.write('{0} -- {1}\n'.format(from_node, node))
                        output.write('{0} [shape=circle]\n'.format(from_node))
                        output.write('{0} [shape=circle]\n'.format(node))
                        max_node = max(max_node, int(node), int(from_node))
                    if next_ranked:
                        rank_nodes = map(str, range(ranked, next_ranked))
                        output.write('{{rank=same {0}}}\n'.format(' '.join(rank_nodes)))
                        ranked = next_ranked
                output.write('{0} [fillcolor=green, style=filled]\n'.format(start_node))
                output.write('{0} [fillcolor=blue, style=filled]\n'.format(end_node))
                output.write('}')
