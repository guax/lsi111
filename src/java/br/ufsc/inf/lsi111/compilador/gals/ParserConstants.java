package br.ufsc.inf.lsi111.compilador.gals;

public interface ParserConstants
{
    int START_SYMBOL = 56;

    int FIRST_NON_TERMINAL    = 56;
    int FIRST_SEMANTIC_ACTION = 89;

    int[][] PARSER_TABLE =
    {
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1,  0,  0, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 29, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 32, -1, -1, -1, -1, -1, -1, -1, 36, 39, -1, 33, 39, -1, -1, 34, -1, 39, 37, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 19, 20, 20, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 79, 78, 82, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 80, 81, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  3, -1, -1, -1, -1, -1,  3,  3, -1, -1,  3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  8, -1, -1, -1,  9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  6,  7, -1, -1,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1,  5,  5, -1, -1,  5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, -1, -1,  1,  1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1 },
        { -1, -1, -1, -1, -1, -1, -1, 48, -1, -1, -1, -1, 48, -1, -1, -1, -1, -1, -1, -1, -1, 48, 48, 48, 48, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 48, 48, 48, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 57, -1, -1, -1, -1, 57, -1, -1, -1, -1, -1, -1, -1, -1, 57, 57, 57, 57, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 57, 57, 57, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 71, -1, -1, -1, -1, 70, -1, -1, -1, -1, -1, -1, -1, -1, 72, 73, 73, 73, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 69, 73, 73, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 12, 13, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 60, 61, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 66, 67, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 68, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, 53, 52, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 56, 55, 54, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 11, -1, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 45, -1, -1, -1, -1, -1, 44, -1, 43, -1, -1, -1, -1, -1, -1, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 45, -1, -1, 45, -1, -1, -1, -1, 45, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 30, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, 46, -1, -1, -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 59, 59, -1, 59, 59, 59, -1, 59, -1, 59, 58, 58, -1, -1, -1, -1, -1, 59, 59, 59, -1, -1, -1, -1, -1, -1, -1, -1, -1, 59, -1, -1, 59, -1, -1, -1, 59, 59, -1, -1, 58, -1, -1, -1, -1, -1, 59, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, 17, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 14, -1, -1, -1, -1, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 65, 65, -1, 65, 65, 65, -1, 65, -1, 65, 65, 65, 64, 64, -1, -1, -1, 65, 65, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, -1, -1, 65, -1, -1, -1, 65, 65, -1, -1, 65, 64, -1, -1, -1, -1, 65, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 50, 50, -1, 49, 49, 49, -1, 50, -1, 50, -1, -1, -1, -1, -1, -1, -1, 49, 49, 49, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, -1, -1, 50, -1, -1, -1, 50, 50, -1, -1, -1, -1, -1, -1, -1, -1, 50, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 77, 77, -1, 77, 77, 77, 75, 77, 76, 77, 77, 77, 77, 77, -1, -1, -1, 77, 77, 77, -1, -1, -1, -1, -1, -1, -1, -1, -1, 77, -1, -1, 77, -1, -1, -1, 77, 77, -1, -1, 77, 77, -1, -1, -1, -1, 77, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 41, -1, -1, 41, -1, -1, -1, -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, 63, -1, -1, -1, -1, 63, -1, -1, -1, -1, -1, -1, -1, -1, 63, 63, 63, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 63, 63, 63, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, 23, 21, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, 24, -1, -1, -1, 22, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 27, -1, 28, -1, -1, -1, -1, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 74, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
    };

    int[][] PRODUCTIONS = 
    {
        { 189,  65,  57,   4 },
        {  61,  64,  63 },
        {  55,  87,  22, 190,   7,  59, 191,   2,  61 },
        {   0 },
        {  26, 192,  69, 193,  16,  86, 194,   2,  64 },
        {   0 },
        {  62,   2,  63 },
        {   0 },
        {  32,  22, 195,  74, 196,   2,  65,  57, 197 },
        {  36,  22, 198,  74, 196,  16,  87, 199,   2,  65,  57, 197 },
        {   8,  70,   9 },
        {   0 },
        {  52, 200,  69, 201,  16,  87, 202,  80 },
        {  53, 203,  69, 201,  16,  87, 202,  80 },
        {   2,  70 },
        {   0 },
        {  22, 204,  79 },
        {   3,  22, 204,  79 },
        {   0 },
        {  22, 205 },
        {  60 },
        {  87 },
        {  54,  59, 206,  18,  59, 207 },
        {  28,  10,  59, 208,  11 },
        {  50,  10,  59, 209,  11,  47,  87, 210 },
        {  35, 211 },
        {  49, 212 },
        {  27, 213 },
        {  29, 214 },
        {  33,  58,  76,  34 },
        {   2,  58,  76 },
        {   0 },
        {  22, 215,  75 },
        {  33,  57,  34 },
        {  37,  66, 216,  38,  58,  84 },
        {  51,  66, 216,  48,  58 },
        {  30,  58,  31,  66, 216 },
        {  40,   8, 217,  69,   9 },
        {  41,   8,  66, 218,  77,   9 },
        {   0 },
        {  39,  58 },
        {   0 },
        { 219,  17,  66, 220 },
        {  10, 221,  66, 222,  11,  17,  66, 220 },
        {   8, 223,  66, 224,  77,   9, 225 },
        { 226 },
        {   3,  66, 227,  77 },
        {   0 },
        {  67, 228,  82 },
        {  73,  67, 229 },
        {   0 },
        {   7 },
        {   6 },
        {   5 },
        {  21 },
        {  20 },
        {  19 },
        {  85, 236,  78 },
        {  71, 237,  85, 238,  78 },
        {   0 },
        {  12, 239 },
        {  13, 240 },
        {  42, 241 },
        {  68, 242,  81 },
        {  72, 243,  68, 244,  81 },
        {   0 },
        {  14, 245 },
        {  15, 246 },
        {  43, 247 },
        {  44, 248,  68, 249 },
        {  13, 250,  68, 251 },
        {   8, 252,  66,   9, 253 },
        {  88, 254 },
        {  60, 255 },
        {  22, 215,  83 },
        {   8, 256,  66, 224,  77,   9, 257 },
        {  10, 221,  66, 258,  11 },
        { 259 },
        {  24, 260 },
        {  23, 261 },
        {  45, 262 },
        {  46, 263 },
        {  25, 264 }
    };

    String[] PARSER_ERROR =
    {
        "",
        "Era esperado fim de programa",
        "Era esperado \";\"",
        "Era esperado \",\"",
        "Era esperado \".\"",
        "Era esperado \">\"",
        "Era esperado \"<\"",
        "Era esperado \"=\"",
        "Era esperado \"(\"",
        "Era esperado \")\"",
        "Era esperado \"[\"",
        "Era esperado \"]\"",
        "Era esperado \"+\"",
        "Era esperado \"-\"",
        "Era esperado \"*\"",
        "Era esperado \"/\"",
        "Era esperado \":\"",
        "Era esperado \":=\"",
        "Era esperado \"..\"",
        "Era esperado \"<>\"",
        "Era esperado \"<=\"",
        "Era esperado \">=\"",
        "Era esperado id",
        "Era esperado num_real",
        "Era esperado num_int",
        "Era esperado literal",
        "Era esperado var",
        "Era esperado booleano",
        "Era esperado cadeia",
        "Era esperado caracter",
        "Era esperado repita",
        "Era esperado ate",
        "Era esperado proc",
        "Era esperado inicio",
        "Era esperado fim",
        "Era esperado inteiro",
        "Era esperado funcao",
        "Era esperado se",
        "Era esperado entao",
        "Era esperado senao",
        "Era esperado leia",
        "Era esperado escreva",
        "Era esperado ou",
        "Era esperado e",
        "Era esperado nao",
        "Era esperado falso",
        "Era esperado verdadeiro",
        "Era esperado de",
        "Era esperado faca",
        "Era esperado real",
        "Era esperado vetor",
        "Era esperado enquanto",
        "Era esperado ref",
        "Era esperado val",
        "Era esperado intervalo",
        "Era esperado const",
        "Era esperado: const, var, proc, inicio, funcao", // programa
        "Era esperado: inicio", // comandos
        "Era esperado: id, repita, inicio, se, leia, escreva, enquanto, ;, ate, fim, senao", // comando
        "Era esperado: id, num_real, num_int, falso, verdadeiro, literal", // constante
        "Era esperado: num_real, num_int, falso, verdadeiro, literal", // constante_explicita
        "Era esperado: const, var, proc, inicio, funcao", // dcl_const
        "Era esperado: proc, funcao", // dcl_proc
        "Era esperado: proc, funcao, inicio", // dcl_procs
        "Era esperado: var, proc, inicio, funcao", // dcl_var
        "Era esperado: const, var, proc, funcao, inicio", // declaracoes
        "Era esperado: (, -, id, num_real, num_int, nao, falso, verdadeiro, literal", // expressao
        "Era esperado: (, -, id, num_real, num_int, nao, falso, verdadeiro, literal", // expsimp
        "Era esperado: (, -, id, num_real, num_int, nao, falso, verdadeiro, literal", // fator
        "Era esperado: id", // lid
        "Era esperado: ref, val", // listapar
        "Era esperado: +, -, ou", // op_add
        "Era esperado: *, /, e", // op_mult
        "Era esperado: >, <, =, <>, <=, >=", // oprel
        "Era esperado: (, ;, :", // parametros
        "Era esperado: (, [, :=, ;, ate, fim, senao", // rcomid
        "Era esperado: ;, fim", // rep_comando
        "Era esperado: \",\",)", // rep_expressao
        "Era esperado: +, -, ou, ;, \",\", >, <, =, ), ], <>, <=, >=, ate, fim, entao, senao, faca", // rep_expsimp
        "Era esperado: \",\", ), :", // rep_id
        "Era esperado: ;,)", // rep_listapar
        "Era esperado: *, /, e, ;, \",\", >, <, =, ), ], +, -, <>, <=, >=, ate, fim, entao, senao, ou, faca", // rep_termo
        "Era esperado: >, <, =, <>, <=, >=, ;, \",\", ), ], ate, fim, entao, senao, faca", // resto_expressao
        "Era esperado: (, [, ;, \",\", >, <, =, ), ], +, -, *, /, <>, <=, >=, ate, fim, entao, senao, ou, e, faca", // rvar
        "Era esperado: senao", // senaoparte
        "Era esperado: (, -, id, num_real, num_int, nao, falso, verdadeiro, literal", // termo
        "Era esperado: booleano, cadeia, caracter, inteiro, real, vetor, intervalo", // tipo
        "Era esperado: booleano, caracter, inteiro, real", // tipo_pre_def
        "Era esperado: id" // variavel
    };
}
