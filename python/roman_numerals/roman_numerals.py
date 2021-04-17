decodex = {'I': 1, 'V': 5, 'X': 10, 'L': 50, 'C': 100, 'D': 500, 'M': 1000}
encodex = {1: 'I', 5: 'V', 10: 'X', 50: 'L', 100: 'C', 500: 'D', 1000: 'M'}


def decode(roman: str) -> int:
    res = decodex[roman[-1]]
    for x in range(len(roman) - 1):
        if decodex[roman[x]] >= decodex[roman[x + 1]]:
            res += decodex[roman[x]]
        else:
            res -= decodex[roman[x]]
    return res


def encode(n: int) -> str:
    k = list(reversed(encodex.keys()))
    i = 0
    res = ""
    while n > 0 and i < len(k):
        while n > 0:
            res += encodex[k[i]]
            n -= k[i]
        if n < 0:
            res = res[:-1]
            res += encodex[k[i + 1]]
            res += encodex[k[i]]
            n += k[i + 1]
        i += 1
    return res
