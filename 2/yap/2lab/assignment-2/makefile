lib.o: lib.asm
	nasm -f elf64 -o lib.o lib.asm

dict.o: dict.asm lib.inc
	nasm -f elf64 -o dict.o dict.asm

main.o: main.asm dict.inc lib.inc word.inc
	nasm -f elf64 -o main.o main.asm

main: main.o dict.o lib.o
	ld -o main main.o dict.o lib.o

test:
	python3 tests.py

