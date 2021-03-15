def interpreter(code: str, iterations: int, width: int, height: int):
    grid = [[0 for _ in range(width)] for _ in range(height)]
    x = y = c = i = 0

    skip_l = [a for a, b in enumerate(code) if b == '[']
    skip_r = [a for a, b in enumerate(code) if b == ']']

    while i < iterations and c in range(len(code)):
        if code[c] == 'n':
            y = (y + height - 1) % height
        elif code[c] == 's':
            y = (y + 1) % height
        elif code[c] == 'e':
            x = (x + 1) % width
        elif code[c] == 'w':
            x = (x + width - 1) % width
        elif code[c] == '*':
            grid[y][x] = (grid[y][x] + 1) % 2
        elif code[c] == '[':
            if grid[y][x] == 0:
                c = skip_r[-skip_l.index(c) - 1] + 1
                i += 1
            else:
                c += 1
            continue
        elif code[c] == ']':
            if grid[y][x] != 0:
                c = skip_l[-skip_r.index(c) - 1]
            else:
                c += 1
            i += 1
            continue
        elif code[c] not in 'nsew*[]':
            c += 1
            continue
        i += 1
        c += 1

    return '\r\n'.join(''.join(map(str, row)) for row in grid)

# def interpreter(code: str, iterations: int, width: int, height: int):
#     grid = [[0 for _ in range(width)] for _ in range(height)]
#     x, y = 0, 0  # row, col
#     pointer_c = 0
#     iter_count = 0
#     print(code, iterations, width, height, file=sys.stderr)
#     lskips = [x for x, y in enumerate(code) if y == '[']
#     rskips = [x for x, y in enumerate(code) if y == ']']
#
#     while iter_count < iterations and 0 <= pointer_c < len(code):
#
#         if y not in range(height) or x not in range(width):
#             print(f'x or y out of bounds: ({x}, {y})')
#             break
#
#         if code[pointer_c] == 'n':
#             y = (y + height - 1) % height
#         elif code[pointer_c] == 's':
#             y = (y + 1) % height
#         elif code[pointer_c] == 'e':
#             x = (x + 1) % width
#         elif code[pointer_c] == 'w':
#             x = (x + width - 1) % width
#         elif code[pointer_c] == '*':
#             grid[y][x] = (grid[y][x] + 1) % 2
#         elif code[pointer_c] == '[':
#             if grid[y][x] == 0:
#                 pointer_c = rskips[-lskips.index(pointer_c) - 1] + 1
#                 iter_count += 1
#                 continue
#                 # loop = 0
#                 # while 0 <= pointer_c < len(code):
#                 #     if code[pointer_c] == '[':
#                 #         loop += 1
#                 #     elif code[pointer_c] == ']':
#                 #         loop -= 1
#                 #     pointer_c += 1
#                 #     if not loop:
#                 #         break
#                 # continue
#         elif code[pointer_c] == ']':
#             if grid[y][x] != 0:
#                 pointer_c = lskips[-rskips.index(pointer_c) - 1]
#                 iter_count += 1
#                 continue
#                 # loop = 0
#                 # while 0 <= pointer_c < len(code):
#                 #     if code[pointer_c] == '[':
#                 #         loop += 1
#                 #     elif code[pointer_c] == ']':
#                 #         loop -= 1
#                 #     if not loop:
#                 #         break
#                 #     pointer_c += 1
#                 # continue
#         elif code[pointer_c] not in 'nsew*[]':
#             pointer_c += 1
#             continue
#         iter_count += 1
#         pointer_c += 1
#     print(f"iterations: {iter_count}")
#     print(f"grid size: {sys.getsizeof(grid)}")
# #     print('\r\n' + str(iter_count).ljust(width, '–'))
# #     print('\r\n'.join(''.join('#' if x else '.' for x in row) for row in grid))
#     return '\r\n'.join(''.join(map(str, row)) for row in grid)


# print(interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 100, 6, 9))
interpreter("news*sw*[ees*[w*w*]n*[n**]**]ws*", 1000, 10000, 1000)
