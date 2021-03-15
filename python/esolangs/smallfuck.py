# []*>*>*>]
# 000
# *, 0
# 100
# >, 1
# 100
# *, 1
# 110
# >, 2
# 110
# *, 2
# 111
# ], 2
# 111
#


def interpreter(code: str, tape: str):
    _tape = [int(b) for b in tape]
    pointer_t = 0
    pointer_c = 0
    while 0 <= pointer_c < len(code):
        if pointer_t >= len(_tape) or pointer_t < 0:
            print("pointer_t out of bounds")
            print(f"code: {code}\ntape: {tape}\npointer_c: {pointer_c}\npointer_t: {pointer_t}")
            break
        if code[pointer_c] == '>':
            pointer_t += 1
        elif code[pointer_c] == '<':
            pointer_t -= 1
        elif code[pointer_c] == '*':
            _tape[pointer_t] = (_tape[pointer_t] + 1) % 2
        elif code[pointer_c] == '[':
            if _tape[pointer_t] == 0:
                nest_level = 0
                while pointer_c < len(code) - 1:
                    if code[pointer_c] == ']':
                        nest_level -= 1
                    elif code[pointer_c] == '[':
                        nest_level += 1
                    pointer_c += 1
                    if nest_level == 0:
                        break
                continue
        elif code[pointer_c] == ']':
            if _tape[pointer_t] != 0:
                nest_level = 0
                while pointer_c >= 0:
                    if code[pointer_c] == '[':
                        nest_level -= 1
                    elif code[pointer_c] == ']':
                        nest_level += 1
                    if nest_level == 0:
                        break
                    pointer_c -= 1
                continue
        pointer_c += 1
        if pointer_c >= len(code) or pointer_c < 0:
            print("pointer_c out of bounds")
            print(f"code: {code}\ntape: {tape}\npointer_c: {pointer_c}\npointer_t: {pointer_t}")
    return ''.join(str(x) for x in _tape)


print(interpreter("*", "00101100") == "10101100")
