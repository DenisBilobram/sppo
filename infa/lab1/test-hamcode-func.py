from task import ham_code
import pytest

@pytest.mark.parametrize("a, b, c", [('1010000', '1000', 2),
                                     ('0110100', '1100', 4),
                                     ('0111010', '0010', 3),
                                     ('0101101', '0101', 4)])
def test_hamcode(a, b, c):
    assert ham_code(a) == f'{b}, ошибка в {c}-ом бите'
