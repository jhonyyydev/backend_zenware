# Prueba Técnica – Backend Developer (Java 8 + Quarkus)

## Estructura del proyecto

```
repo/
├── app/               # código fuente Quarkus
├── src/test/          # pruebas JUnit
├── logs/diagnostico.log
├── pom.xml            # build Maven
└── README.md          # instrucciones y respuestas
```

---

## Cómo ejecutar la aplicación

1. Clonar el repositorio  
2. Ejecutar con Maven:  
```
mvn clean quarkus:dev
```
3. La API quedará disponible en: `http://localhost:8080`

---

## Endpoints disponibles y ejemplos para probar con curl

| Verbo | Ruta               | Descripción                  |
|-------|--------------------|------------------------------|
| GET   | /productos         | Lista todos los productos     |
| POST  | /productos         | Crea un producto (JSON)       |
| GET   | /productos/{id}    | Obtiene producto por id       |
| DELETE| /productos/{id}    | Elimina producto por id       |

### Ejemplo GET todos los productos
```bash
curl -X GET http://localhost:8080/productos
```

### Ejemplo POST crear producto

```bash
curl -X POST http://localhost:8080/productos \
  -H "Content-Type: application/json" \
  -d '{"id":1,"nombre":"Producto1","precio":100.0}'
```

### Ejemplo GET producto por id (éxito)

```bash
curl -X GET http://localhost:8080/productos/1
```

### Ejemplo GET producto por id (no encontrado)

```bash
curl -X GET http://localhost:8080/productos/999
```

Respuesta:

```json
{"code":404,"message":"No encontrado"}
```

### Ejemplo DELETE producto por id (éxito)

```bash
curl -X DELETE http://localhost:8080/productos/1
```

### Ejemplo DELETE producto por id (no encontrado)

```bash
curl -X DELETE http://localhost:8080/productos/999
```

Respuesta:

```json
{"code":404,"message":"No encontrado"}
```

---

## Respuestas a secciones 3, 4 y 5

### 3. Diagnóstico de logs

**Archivo analizado:** `logs/diagnostico.log`
Contenido relevante:

```
2025-01-15 10:14:07,321 ERROR [io.qua.sch.run.SimpleScheduler] (SimpleScheduler) Error processing job
java.lang.NumberFormatException: For input string: "abc"
    at java.base/Integer.parseInt(Integer.java:614)
```

**1. Causa de la excepción:**
La excepción `NumberFormatException` ocurre porque la aplicación intentó convertir la cadena `"abc"` a un número entero usando `Integer.parseInt()`. Como `"abc"` no es un número válido, se lanza esta excepción. Esto indica que en alguna parte del código se está procesando una entrada que debería ser numérica, pero que no cumple con ese formato.

**2. Estrategia para prevenirla:**

* Validar que las entradas que se esperan numéricas contengan solo dígitos antes de intentar la conversión.
* Usar Bean Validation para asegurar el tipo y formato de los datos recibidos en los endpoints REST.
* Capturar esta excepción y devolver un mensaje de error claro al cliente.
* Implementar validaciones en el front-end o cliente para evitar enviar datos inválidos (en caso de necesitarlo).

**3. Nivel de log recomendado:**
El nivel **ERROR** es el adecuado para este tipo de excepciones porque representa un fallo que impide la ejecución correcta de una operación crítica. Permite que el equipo de desarrollo identifique y solucione el problema rápidamente.

---

### 4. AWS

**¿Cuándo usar EC2 vs Lambda?**

* Usaría **EC2** en casos como: Aplicaciones que requieren más de 15 min de ejecución, control del OS, 
  cargas de trabajo constantes donde el costo fijo es menor, o configuraciones específicas.
* Usaría **Lambda** en casos como: Funciones cortas (<15 min), cargas esporádicas, integración con eventos AWS, 
  cuando quiero zero-administration y escalabilidad automática.

**Tres servicios de observabilidad y su propósito:**

* **CloudWatch:** Monitoriza métricas y logs de recursos AWS.
* **X-Ray:** Traza y depura peticiones distribuidas en aplicaciones.
* **CloudTrail:** Registra acciones y eventos realizados en la cuenta AWS para auditoría.

---

### 5. SQL

**Consulta para obtener 5 productos con precio > 100000 ordenados descendente:**

```sql
SELECT * FROM productos
WHERE precio > 100000
ORDER BY precio DESC
LIMIT 5;
```

**¿Cuándo un INDEX(nombre) acelera búsquedas?**
Un índice en la columna `nombre` mejora la búsqueda cuando se realizan consultas filtrando o buscando productos por ese campo, ya que reduce el tiempo de escaneo al evitar un recorrido completo de la tabla.

---

## Notas finales

* La persistencia está implementada en memoria usando un `Map`.
* Se usa Bean Validation para validar `nombre` y `precio`.
* El manejo de errores devuelve siempre JSON con `{code, message}` para consistencia.
* Incluyo dos tests principales para validar el flujo feliz y casos de error 404.

---

**Jhonatan Veloza**
Backend Developer
Zenware Prueba Técnica