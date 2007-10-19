sed "s/%2/%3/g" %1 > %1.tmp
del %1
move %1.tmp %1