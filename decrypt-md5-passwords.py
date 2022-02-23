import itertools
import hashlib

def rule_asc(n):
    a = [0 for i in range(n + 1)]
    k = 1
    a[1] = n
    while k != 0:
        x = a[k - 1] + 1
        y = a[k] - 1
        k -= 1
        while x <= y:
            a[k] = x
            y -= x
            k += 1
        a[k] = x + y
        yield a[:k + 1]
        
# print(list((rule_asc(4))))
# x = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']  
        
x = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']  
    
y = [(hashlib.md5(''.join(p).encode()).hexdigest(), ''.join(p)) for p in itertools.product(x, repeat=5)]

for i in range(len(y)):
    if y[i][0] == '86aa400b65433b608a9db30070ec60cd':
        print(y[i][1])
    
def crack(z):
    x = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']  
    y = [(hashlib.md5(''.join(p).encode()).hexdigest(), ''.join(p)) for p in itertools.product(x, repeat=5)]
    
    for i in range(len(y)):
        if y[i][0] == z:
            return y[i][1]
            
    
print(crack('86aa400b65433b608a9db30070ec60cd'))
