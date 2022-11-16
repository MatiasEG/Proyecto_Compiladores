.CODE
   PUSH mainMainModule
   CALL
   HALT

# ---------------- simple_heap_init ---------------- 
simple_heap_init:RET 0 # Inicializacion simple del .heap

# ---------------- simple_malloc ---------------- 
simple_malloc:
              LOADFP # Inicializacion unidad
              LOADSP
              STOREFP # Finaliza inicializacion del RA
              LOADHL # hl
              DUP # hl
              PUSH 1
              ADD # hl + 1
              STORE 4 # Guarda resultado (puntero a base del bloque)
              LOAD 3 # Carga cantidad de celdas a alojar (parametro)
              ADD
              STOREHL # Mueve el heap limit (hl)
              STOREFP
              RET 1 # Retorna eliminando el parametro

.DATA

VT_A: DW m1A

.CODE

# ---------------- AConstructor ---------------- 
AConstructor:
             LOADFP
             LOADSP
             STOREFP
             FMEM 0 # Se elimina el espacio reservado para las variables locales.
             STOREFP
             RET 1

# ---------------- m1A ---------------- 
m1A:
    LOADFP
    LOADSP
    STOREFP
    PUSH 56
    LOAD 0 # Apilo el valor de la variable.
    PUSH printIlnSystem
    CALL
    FMEM 1 # Libero los lugares de las var locales
    LOAD 0 # Apilo el valor de la variable.
    STORE 4 # Se coloca el valor de retorno en su ubicacion reservada
    STOREFP 
    RET 1 # Efectuamos el retorno liberando la cantidad de parametros
    FMEM 1 # Se elimina el espacio reservado para las variables locales.
    STOREFP
    RET 1

.DATA

VT_MainModule: NOP

.CODE

# ---------------- mainMainModule ---------------- 
mainMainModule:
               LOADFP
               LOADSP
               STOREFP
               RMEM 1 # Resultado de malloc, referencia al CIR de un objeto
               PUSH 2 # Parametro malloc, cant de atributos del objeto + 1 para VT
               PUSH simple_malloc
               CALL
               DUP # de la referencia al nuevo CIR
               PUSH VT_A
               STOREREF 0 # Consume una de las dupicas de la referencia al CIR
               DUP # de la referencia al objeto
               PUSH AConstructor
               CALL # Invoco el constructor
               LOAD 0 # Apilo el valor de la variable.
               RMEM 1 # Lugar de retorno
               SWAP # Muevo this al tope de la pila
               DUP # Duplico this
               LOADREF 0 # Consumo un this y lo reemplazo por su VT
               LOADREF 0 # Lugar de retorno
               CALL # Realizo la llamada a metodo dinamico
               PUSH 4
               ADD
               PUSH printISystem
               CALL
               FMEM 1 # Se elimina el espacio reservado para las variables locales.
               STOREFP
               RET 0

# ---------------- MainModuleConstructor ---------------- 
MainModuleConstructor:
                      LOADFP
                      LOADSP
                      STOREFP
                      FMEM 0 # Se elimina el espacio reservado para las variables locales.
                      STOREFP
                      RET 1

# ---------------- debugPrintObject ---------------- 
debugPrintObject:
                 LOADFP
                 LOADSP
                 STOREFP
                 LOAD 3
                 IPRINT
                 PRNLN
                 STOREFP
                 RET 1

# ---------------- readSystem ---------------- 
readSystem:
           LOADFP
           LOADSP
           STOREFP
           READ
           STORE 3
           STOREFP
           RET 0

# ---------------- printBSystem ---------------- 
printBSystem:
             LOADFP
             LOADSP
             STOREFP
             LOAD 3
             BPRINT
             STOREFP
             RET 1

# ---------------- printCSystem ---------------- 
printCSystem:
             LOADFP
             LOADSP
             STOREFP
             LOAD 3
             CPRINT
             STOREFP
             RET 1

# ---------------- printISystem ---------------- 
printISystem:
             LOADFP
             LOADSP
             STOREFP
             LOAD 3
             IPRINT
             STOREFP
             RET 1

# ---------------- printSSystem ---------------- 
printSSystem:
             LOADFP
             LOADSP
             STOREFP
             LOAD 3
             SPRINT
             STOREFP
             RET 1

# ---------------- printlnSystem ---------------- 
printlnSystem:
              LOADFP
              LOADSP
              STOREFP
              PRNLN
              STOREFP
              RET 0

# ---------------- printBlnSystem ---------------- 
printBlnSystem:
               LOADFP
               LOADSP
               STOREFP
               LOAD 3
               BPRINT
               PRNLN
               STOREFP
               RET 1

# ---------------- printClnSystem ---------------- 
printClnSystem:
               LOADFP
               LOADSP
               STOREFP
               LOAD 3
               CPRINT
               PRNLN
               STOREFP
               RET 1

# ---------------- printIlnSystem ---------------- 
printIlnSystem:
               LOADFP
               LOADSP
               STOREFP
               LOAD 3
               IPRINT
               PRNLN
               STOREFP
               RET 1

# ---------------- printSlnSystem ---------------- 
printSlnSystem:
               LOADFP
               LOADSP
               STOREFP
               LOAD 3
               SPRINT
               PRNLN
               STOREFP
               RET 1

