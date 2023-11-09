# Exemen castores
---
# Status
Flavio Urdiales Mena 
Fecha creacion 08/11/2023
Fecha finalizacion: 09/11/2023
Status: Terminado

---

# Usuarios de prueba:

    user:usertest
    password: 1234
    tipo:Interno
    
    user: usertest2
    password: 1234
    tipo:Externo

---

# Librerias:

| Librería                             | Install         |
| ------------------------------------ | --------------- |
| [axios](https://www.npmjs.com/package/axios)                             | `npm install axios`         |
| [bootstrap](https://www.npmjs.com/package/bootstrap)                     | `npm install bootstrap`     |
| [bootstrap-icons](https://www.npmjs.com/package/bootstrap-icons)         | `npm install bootstrap-icons` |
| [@ng-bootstrap/ng-bootstrap](https://www.npmjs.com/package/@ng-bootstrap/ng-bootstrap) | `npm install @ng-bootstrap/ng-bootstrap` |
| [sweetalert2](https://www.npmjs.com/package/sweetalert2)                 | `npm install sweetalert2`  |

---

## Cambios en los archivos

En el archivo `feed.component.ts`, realiza el siguiente cambio en la línea 15:

```typescript
urlservices: any = "http://tudireccionlocal"

```
En el archivo `login.component.ts`, realiza el siguiente cambio en la línea 11:

```typescript
urlservices: any = "http://tudireccionlocal"

```

## 🛠️ Funcionalidades del proyecto

- `Login`: Login, se debe de ingresar para poder publicar notas, comentar y responder comentarios, ( Se ingresa con alguno de los dos usuarios de prueba ) los usuarios internos puedes publicar,comentar y responder, mientras que los externos no puedes publicar notas. Al iniciar sesion se Genera una variable usuario en el localsession la cual se encripta. 
```typescript
        localStorage.setItem("usuario", this.encryptData(response.data));
        
        
        encryptData(data: any): string {
            const jsonString = JSON.stringify(data);
            const encodedString = btoa(jsonString);
            return encodedString;
        }

```
- `Feed o Home`: Feed o Home, Cuanta con seccion para crear una nueva noticia se explica en `Crear Nota`,  Una seccion de noticas Publicadas Se explica en `Noticias`. 

- `Crear Nota`: Para crear una nueva nota existen dos formas. 
1.-En feed en la primera seccion se encuentra un input que al darle click despliega un seetalert tipo modal. en la cual te pide que ingreses la notica, este pequeño formulario toma el id del personal interno desde el localsession, desencriptando la cadadena almacenada con los datos del usuario usaundo la funcion 
```typescript
 decryptData(encodedString: string): any {
    const jsonString = atob(encodedString);
    return JSON.parse(jsonString);
  }

```
. Tiene sus debidas validaciones como el que la nota no vaya vacia, y valida que no tenga caracteres como <> para intentar evitar un posibles ataques XSS de manera sencilla.
```typescript
  bloquearXSS = (cadena: any) => {
    let resultado = cadena.replace(/</g, "&lt;").replace(/>/g, "&gt;");
    return resultado;
  }


```

Tambien valida que el usuario sea interno de no serlo no despliega la modal y despliega un mensaje de error. Aunque eesta seccion esta oculta para los usuarios externos.

- `Noticias`: Se despliega un listado de las noticias en un `CARD` el cual contiene el nombre del usuario, la fecha y hora de la nota, el texto de la nota, y un boton de comentarios con un pequeño numero de cuantos comentarios se han realizado a esta nota, al dar click en `COMENTARIOS` se despliega una seccion con los comentarios de la nota, y a su vez se pone en la parte superar un input para agregar un comentario al dar click se agrega el comentario validando que no contenga un null o empy string y validando con la cunion bloquearXSS para intentar evitar posibles ataques XSS. los `COMENTARIOS` como las notas tienen un pequeño boton con respuestas y la cantidad de respuestas , al dar click en `RESPUESTAS` se despliega una seccion con las respuestas del comentario.

# Nota: 
Al dar click en comentarios o respuestas se abre una funcion la cual con un intervalo se 5s manda a llamar a los comentarios o respuestas dependiendo de a que se le haya dado click, esto para ver los posibles comentarios o respuestas que se efectuen en otro navegador/dispositivo. 

```typescript
abrirSocket = () => {
    let url = this.urlservices + "Noticias";
    if (this.socket.activate != true) {
      return;
    }
    axios.get(url).then(
      response => {


        switch (this.socket.tipo) {
          case "comentario":

            this.noticias.forEach((element: any, index: any) => {
              if (element.identificador == this.socket.id) {
                if (element.comentarios.length != response.data[index].comentarios.length) {
                  element.comentarios = response.data[index].comentarios;
                }
              }
            }
            );


            break;
          case "respuesta":
            this.noticias.forEach((element: any, index: any) => {

              element.comentarios.forEach((comentario: any, index2: any) => {
                if (comentario.identificador == this.socket.id) {
                  if (comentario.respuestas.length != response.data[index].comentarios[index2].respuestas.length) {
                    comentario.respuestas = response.data[index].comentarios[index2].respuestas;
                  }
                }
              }
              );

            }
            );
            break;
          default:
            break;
        }

      }
    ).catch(
      error => {
        console.log(error);
      }
    )

  }

  intervalo = setInterval(() => {
    this.abrirSocket();
  }, 5000);


```




