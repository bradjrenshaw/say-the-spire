cp changes.md docs_src/src/changes.md
mdbook build docs_src
cp -r docs_src/book/* docs