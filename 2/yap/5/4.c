/* printer.c */

#include <inttypes.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


struct AST {
  enum AST_type { AST_BINOP, AST_UNOP, AST_LIT } type;
  union {
    struct binop {
      enum binop_type { BIN_PLUS, BIN_MINUS, BIN_MUL, BIN_DIV } type;
      struct AST *left, *right;
    } as_binop;
    struct unop {
      enum unop_type { UN_NEG } type;
      struct AST *operand;
    } as_unop;
    struct literal {
      int64_t value;
    } as_literal;
  };
};

/* DSL */
static struct AST *newnode(struct AST ast) {
  struct AST *const node = malloc(sizeof(struct AST));
  *node = ast;
  return node;
}

struct AST _lit(int64_t value) {
  return (struct AST){AST_LIT, .as_literal = {value}};
}

struct AST *lit(int64_t value) {
  return newnode(_lit(value));
}
struct AST _unop(enum unop_type type, struct AST *operand) {
  return (struct AST){AST_UNOP, .as_unop = {type, operand}};
}

struct AST *unop(enum unop_type type, struct AST *operand) {
  return newnode(_unop(type, operand));
}

struct AST _binop(enum binop_type type, struct AST *left, struct AST *right) {
  return (struct AST){AST_BINOP, .as_binop = {type, left, right}};
}
struct AST *binop(enum binop_type type, struct AST *left, struct AST *right) {
  return newnode(_binop(type, left, right));
}
#define DECLARE_BINOP(fun, code)                                               \
  struct AST *fun(struct AST *left, struct AST *right) {                       \
    return binop(BIN_##code, left, right);                                     \
  }
DECLARE_BINOP(add, PLUS)
DECLARE_BINOP(mul, MUL)
DECLARE_BINOP(sub, MINUS)
DECLARE_BINOP(divide, DIV)

#undef DECLARE_BINOP
#define DECLARE_UNOP(fun, code)                                                \
  struct AST *fun(struct AST *operand) {                                       \
    return unop(UN_##code, operand);                                           \
  }
DECLARE_UNOP(neg, NEG)
#undef DECLARE_UNOP
/* printer */

static const char *BINOPS[] = {
    [BIN_PLUS] = "+", [BIN_MINUS] = "-", [BIN_MUL] = "*", [BIN_DIV] = "/"};
static const char *UNOPS[] = {[UN_NEG] = "-"};

typedef void(printer)(FILE *, struct AST *);

void print(FILE *f, struct AST *ast);

void print_binop(FILE *f, struct AST *ast) {
  fprintf(f, "(");
  print(f, ast->as_binop.left);
  fprintf(f, ")");
  fprintf(f, "%s", BINOPS[ast->as_binop.type]);
  fprintf(f, "(");
  print(f, ast->as_binop.right);
  fprintf(f, ")");
}
void print_unop(FILE *f, struct AST *ast) {
  fprintf(f, "(%s", UNOPS[ast->as_unop.type]);
  print(f, ast->as_unop.operand);
  fprintf(f, ")");
}
void print_lit(FILE *f, struct AST *ast) {
  fprintf(f, "%" PRId64, ast->as_literal.value);
}

static printer *ast_printers[] = {  
    [AST_BINOP] = print_binop, [AST_UNOP] = print_unop, [AST_LIT] = print_lit};

void print(FILE *f, struct AST *ast) {
  if (ast)
    ast_printers[ast->type](f, ast);
  else
    fprintf(f, "<NULL>");
}

int main() {
  struct AST *num999 = lit(999);
  struct AST *num728 = lit(728);

  struct AST *sum_expr1 = add(num999, num728);
  print(stdout, sum_expr1);
  printf("\n");

  struct AST *num4 = lit(4);
  struct AST *num2 = lit(2);
  struct AST *num9 = lit(9);

  struct AST *mul_expr1 = mul(num2, num9);
  struct AST *sum_expr2 = add(num4, mul_expr1);

  print(stdout, sum_expr2);
  printf("\n");

  struct AST *num3 = lit(3);
  struct AST *num5 = lit(5);

  struct AST *sum_expr3 = add(num3, num5);

  struct AST *num7 = lit(7);

  struct AST *div_expr1 = divide(num9, num7);

  struct AST *mul_expr2 = mul(sum_expr3, div_expr1);
  print(stdout, mul_expr2);
  printf("\n");

  return 0;
}
