from typing import List


a = [[x + 1 for x in range(3 * y, 3 * (y + 1))] for y in range(3)]


def snail(snail_map: List[List[int]]) -> List[int]:
    result, i = [], -1
    while len(snail_map) != 0:
        n, i = len(snail_map), i + 1
        if i % 4 == 0:
            result += snail_map[0]
            del snail_map[0]
        elif i % 4 == 1:
            for j in range(n):
                result.append(snail_map[j][n])
                del snail_map[j][n]
        elif i % 4 == 2:
            result += snail_map[-1][::-1]
            del snail_map[-1]
        elif i % 4 == 3:
            for j in range(n - 1, -1, -1):
                result.append(snail_map[j][0])
                del snail_map[j][0]
    return result


print(snail(a))
