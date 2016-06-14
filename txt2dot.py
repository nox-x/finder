#!/usr/bin/env python3

from os import listdir

for path in listdir('.'):
    if path.startswith('graph'):
        with open(path) as input:
            with open('dot/{0}.dot'.format(path), 'w') as output:
                output.write('digraph G {\n')
                for line in input:
                    output.write(line.replace(' ', ' -> '))
                output.write('}')
