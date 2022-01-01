grammar CPP;

program: include* function* EOF;

include : '#include' '<' IDENTIFIER '>';

function
    : (type | VOID) IDENTIFIER parameters
      (block | ';')
    ;

parameters: '(' parametrList? (',' defaultParameterList)? ')';
parametrList: parameter (',' parameter)*;
parameter: type variableId;
defaultParameterList: defaultParameter (',' defaultParameter)*;
defaultParameter: type variableId '=' expression;

variables: variable (',' variable)*;
variable: variableId ('=' variableInitializer)?;
variableId: IDENTIFIER;
variableInitializer: expression;

block: '{' blockStatement* '}';
blockStatement
    : type variables ';' #varBlockStatement
    | statement #statementBlockStatement
    ;
statement
    : IF '(' expression ')' '{' succBranch=statement '}' (ELSE '{' failBranch=statement '}')? #ifStatement
    | WHILE '(' expression ')' statement #whileStatement
    | RETURN? expression? ';' #expressionStatement
    ;


expressionList: expression (',' expression)*;
functionCall: (IDENTIFIER) '(' expressionList? ')';
expression
    : primary #primaryExpression
    | functionCall #functionCallExpression
    | prefix=('+'|'-'|'++'|'--') expression #opExpression
    | prefix=('~'|'!'|'*'|'&') expression #opExpression
    | expression bop=('*'|'/'|'%') expression #opExpression
    | expression bop=('+'|'-') expression #opExpression
    | expression bop=('<<'|'>>') expression #opExpression
    | expression bop=('<='|'>='|'>'|'<'|'=='|'!=') expression #opExpression
    | expression bop=('&'|'^'|'|'|'&&'|'||') expression #opExpression
    | <assoc=right> expression bop=('='|'+='|'-='|'*='|'/='|'&='|'|='|'^='|'%=') expression #opExpression
    ;

primary
    : '(' expression ')' #parenthesisPrimary
    | literal            #literalPrimary
    | IDENTIFIER         #idPrimary
    ;


type: constType | primitiveType;
constType: 'const' primitiveType;

literal
    : DECIMAL_LITERAL
    | FLOAT_LITERAL
    | STRING_LITERAL
    | BOOL_LITERAL
    ;

primitiveType
    : 'bool'
    | 'char'
    | 'double'
    | 'float'
    | 'int'
    | 'long'
    | 'long long'
    | 'string'
    ;

VOID:               'void';
IF:                 'if';
ELSE:               'else';
WHILE:              'while';
RETURN:             'return';


DECIMAL_LITERAL:    ('0' | [1-9] (Digits? | '_'+ Digits)) [lL]?;
FLOAT_LITERAL:      (Digits '.' Digits? | '.' Digits) [fFdD]?
             |       Digits ([fFdD])
             ;

BOOL_LITERAL:       'true' | 'false';
STRING_LITERAL:     '"' (~["\\\r\n])* '"';

WS:                 [ \t\r\n]+ -> channel(HIDDEN);

IDENTIFIER:         NAME ('::' NAME)*;
NAME:         Letter (Letter | [0-9])*;

fragment Digits: [0-9] ([0-9_]* [0-9])?;
fragment Letter: [a-zA-Z_];