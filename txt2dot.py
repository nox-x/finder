#!/usr/bin/env python3

from os import listdir

for path in listdir('.'):
    if path.startswith('graph'):
        with open(path) as input:
            with open('dot/{0}.dot'.format(path), 'w') as output:
                output.write('graph G {\n')
                first = True
                start_node = None
                end_node = None
                for line in input:
                    if first:
                        first = False
                        start_node, end_node = line.split()
                        continue
                    nodes = line.split()
                    from_node = nodes[0]
                    to_nodes = nodes[1:]
                    for node in to_nodes:
                        output.write('{0} -- {1}\n'.format(from_node, node))
                output.write('{0} [fillcolor=green, style=filled]\n'.format(start_node))
                output.write('{0} [fillcolor=blue, style=filled]\n'.format(end_node))
                output.write('}')
