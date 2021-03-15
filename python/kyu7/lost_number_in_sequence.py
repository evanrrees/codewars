from typing import Tuple, List


def find_deleted_number(arr: List[int], mixed_arr: List[int]) -> int:
    return list(set(arr) - set(mixed_arr))[0] if len(arr) != len(mixed_arr) else 0

