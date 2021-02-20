#!/bin/sh

echo 'pre-push hook:'

cd basic
status=`git status|grep 'nothing to commit'`
if [ -n "status" ]; then
    echo "git changed"
    git add .
    git commit -m "update submodule by pre-commit hook"
    git push
fi

exit -1