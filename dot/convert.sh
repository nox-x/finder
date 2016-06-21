#!/bin/bash

./txt2dot.py
mkdir out
mkdir out/svg
mkdir out/png
for name in *; do dot -Tpng $name -o out/png/$name.png; done
for name in *; do dot -Tsvg $name -o out/svg/$name.svg; done
