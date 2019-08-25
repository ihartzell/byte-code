Isaac Hartzell
hartzell.14@wright.edu

The '+' and '*' operators for all the expressions are left associative. The program works the same as the appletviewer which works left to right,
so the expressions are left associative for '+' and '*' with '*' having precedence over '+.'

expr.dat test expression 1: i
1.jbc Output:
iload_0
i2d

expr.dat test expression 2: j
2.jbc Output:
iload_3
i2d

expr.dat test expression 3: a
3.jbc Output:
dload_1

expr.dat test expression 4: (i)
4.jbc Output:
iload_0
i2d

expr.dat test expression 5: ((j))
5.jbc Output:
iload_3
i2d

expr.dat test expression 6: (((a)))
6.jbc Output:
dload_1

expr.dat test expression 7: (((a)))*(((i)))+(((j)))
7.jbc Output:
dload_1
iload_0
i2d
dmul
iload_3
i2d
dadd

expr.dat test expression 8: i+i
8.jbc Output:
iload_0
iload_0
iadd
i2d

expr.dat test expression 9: i+j
9.jbc Output:
iload_0
iload_3
iadd
i2d

expr.dat test expression 10: i+a
10.jbc Output:
iload_0
i2d
dload_1
dadd

expr.dat test expression 11: j+i
11.jbc Output:
iload_3
iload_0
iadd
i2d

expr.dat test expression 12: a+i
12.jbc Output:
dload_1
iload_0
i2d
dadd

expr.dat test expression 13: i*i
3.jbc Output:
iload_0
iload_0
imul
i2d

expr.dat test expression 14: i*j
14.jbc Output:
iload_0
iload_3
imul
i2d

expr.dat test expression 15: i*a 
15.jbc Output:
iload_0
i2d
dload_1
dmul

expr.dat test expression 16: j*a
16.jbc Output:
iload_3
i2d
dload_1
dmul

expr.dat test expression 17: a*i
17.jbc Output:
dload_1
iload_0
i2d
dmul

expr.dat test expression 18: i+i+i+i+i
18.jbc Output:
iload_0
iload_0
iadd
iload_0
iadd
iload_0
iadd
iload_0
iadd
i2d

expr.dat test expression 19: a+a+a+a+a
19.jbc Output:
dload_1
dload_1
dadd
dload_1
dadd
dload_1
dadd
dload_1
dadd

expr.dat test expression 20: j*j*j*j*j
20.jbc Output:
iload_3
iload_3
imul
iload_3
imul
iload_3
imul
iload_3
imul
i2d

expr.dat test expression 21: a*a*a*a*a
21.jbc Output:
dload_1
dload_1
dmul
dload_1
dmul
dload_1
dmul
dload_1
dmul


expr.dat test expression 22: i*j+a*a
22.jbc Output:
iload_0
iload_3
imul
i2d
dload_1
dload_1
dmul
dadd

expr.dat test expression 23: a+j+j+a
23.jbc Output:
dload_1
iload_3
i2d
dadd
iload_3
i2d
dadd
dload_1
dadd

expr.dat test expression 24: (a*a)+(j*j) 
24.jbc Output:
dload_1
dload_1
dmul
iload_3
iload_3
imul
i2d
dadd

expr.dat test expression 25: (i+i)+(a+j)
25.jbc Output:
iload_0
iload_0
iadd
i2d
dload_1
iload_3
i2d
dadd
dadd

expr.dat test expression 26: i+(a+j)
26.jbc Output:
iload_0
i2d
dload_1
iload_3
i2d
dadd
dadd

expr.dat test expression 27: (i+a)+j
27.jbc Output:
iload_0
i2d
dload_1
dadd
iload_3
i2d
dadd

expr.dat test expression 28: i*(a*j)
28.jbc Output:
iload_0
i2d
dload_1
iload_3
i2d
dmul
dmul

expr.dat test expression: (i*a)*j
29.jbc Output:
iload_0
i2d
dload_1
dmul
iload_3
i2d
dmul

expr.dat test expression 30: ((i+j))*(((a)))+((j*j))
30.jbc Output:
iload_0
iload_3
iadd
i2d
dload_1
dmul
iload_3
iload_3
imul
i2d
dadd

expr.dat test expression 31: i+a*(((a)*i+j))
31.jbc Output:
iload_0
i2d
dload_1
dload_1
iload_0
i2d
dmul
iload_3
i2d
dadd
dmul
dadd





 
 
