ORG 0x0000
V0:	word $TIMERINT, 0x180
V1:	word $SHOWINT, 0x180
V2:	word $DEFAULT, 0x180


ORG 0x0020
NOW:	word 0x0000
NUM:	word 0xFFFF

START:	DI
	LD #10
	OUT 0x0
	LD #8
	OUT 0x1
	LD #9
	OUT 0x3
	EI
PROG:	LOOP NUM
	JUMP PROG

TIMERINT: PUSH
	LD #10
	OUT 0x0
	LD $NOW
	INC
	ST $NOW
	POP
	IRET

SHOWINT:	LD $NOW
	OUT 0x2
	IRET

DEFAULT:	IRET
