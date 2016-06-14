for name in *; do dot -Tpng $name -o /srv/www.wegenerd.de/dot/png/$name.png; done
for name in *; do dot -Tsvg $name -o /srv/www.wegenerd.de/dot/svg/$name.svg; done
