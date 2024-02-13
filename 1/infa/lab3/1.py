import re

# вариант 8<)
t1 = "X-):-(X<P8<):-{|[<{O" # 1
t2 = "X-):-(X<P:-{|[<{O8<):-O" # 1
t3 = "8<)X-):-(X<P:-{|[<{O8<):-O" # 2
t4 = "X-):-(X<P8<):-{|[<{O8<):-{X-)8<)" # 3
t5 = "X-):-(X<P:-{|[<{O:-{X-)" # 0

regexp = "8\<\)"
for t in [t1, t2, t3, t4, t5]:
    res = re.findall(regexp, t)
    print(len(res))