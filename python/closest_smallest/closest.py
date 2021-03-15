from itertools import combinations_with_replacement


def closest(s: str):
    triples = sorted([(sum(map(int, n)), i, int(n)) for i, n in enumerate(s.split(' '))], key=lambda x: (x[0], x[1]))
    keep1, keep2 = triples[0], triples[1]
    for a, b in combinations_with_replacement(triples):
        diff = abs(a[0] - b[0]) - abs(keep1[0] - keep2[0])



    pass

