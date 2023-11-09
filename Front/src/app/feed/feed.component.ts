import { Component } from '@angular/core';
import axios from 'axios';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent {



  noticias: any = [];
  usuario: any = []
  urlservices: any = "http://192.168.1.39:8080/"
  looged: any = false;
  socket: any = []

  constructor() { }

  ngOnInit(): void {

    if (localStorage.getItem("usuario") != null) {
      this.usuario = this.decryptData(localStorage.getItem("usuario") || '{}');
      this.looged = true;
    }
    this.getNoticias();

  }
  bloquearXSS = (cadena: any) => {
    let resultado = cadena.replace(/</g, "&lt;").replace(/>/g, "&gt;");
    return resultado;
  }

  decryptData(encodedString: string): any {
    const jsonString = atob(encodedString);
    return JSON.parse(jsonString);
  }

  getNoticias = () => {

    let url = this.urlservices + "Noticias";

    axios.get(url).then(
      response => {

        response.data.sort(function (a: any, b: any) {
          return new Date(b.fechaPublicacion).getTime() - new Date(a.fechaPublicacion).getTime()
        });

        this.noticias = response.data;

      }
    ).catch(
      error => {
        console.log(error);
      }
    )

  }


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


  comentar = (id: any) => {

    let divname = "comentarios" + id;
    const comments = document.getElementById(divname);

    if (comments != null) {

      if (comments.hidden == true) {
        comments.hidden = false;
        this.socket = {
          "tipo": "comentario",
          "id": id,
          "activate": true
        }
      } else {
        comments.hidden = true;
        this.socket = {
          "tipo": "comentario",
          "id": id,
          "activate": false
        }
      }

    }

  }

  close = (id: any) => {

    let divname = "comentarios" + id;
    const comments = document.getElementById(divname);
    if (comments != null) {

      this.socket = {
        "tipo": "comentario",
        "id": id,
        "activate": false
      }

      comments.hidden = true;



    }

  }


  responder = (id_comentario: any, id_respuesta: any) => {


    let divname = "respuestasnota" + id_respuesta + "comentario" + id_comentario;
    const comments = document.getElementById(divname);
    if (comments != null) {

      if (comments.hidden == true) {
        comments.hidden = false;
        this.socket = {
          "tipo": "respuesta",
          "id": id_comentario,
          "activate": true
        }
      } else {
        comments.hidden = true;
        this.socket = {
          "tipo": "respuesta",
          "id": id_comentario,
          "activate": false
        }
      }

    }

  }

  nuevaNota = () => {
    let url = this.urlservices + "Noticias";

    if (this.usuario.interno != true) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Solo usuarios internos pueden publicar',
      })
      return;
    }

    document.getElementById("nuevaNota")?.blur();

    Swal.fire({
      title: '<div class="text-center">Nueva Noticia</div><hr>',
      html: '<div class="container"><div class="row"><div class="col-auto"> <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAH0AAAB9CAMAAAC4XpwXAAAANlBMVEX///+ZmZmTk5OWlpaQkJDk5OT8/Py8vLzExMT39/fZ2dmmpqaurq7MzMzu7u6hoaG1tbXT09MwSnMzAAADsklEQVRoge1a65KrIAyuEPCCVPv+L7va1l0vJCSI7Zk5fDM7+6f4Qe6B3G4FBQUFBQUFBf89bGs639QzGt+Z1n6O2vh+UKCU0jOm/wDV6M0HmNuuB9C62kNrgL5rL+U2Y6WOzH87qMZJAtdowXaOoF424LpL2DsHEeoXv3Jddu47j/sJcPes3LaGmMw354c6o/jNoATcT/4hmwP6gIdF6fUjC/ckdTH3jCzSt71U6gtUf5q+HeRSX6CHk7HvDPlpenuKfKY/IXzbnyM/p/uaNLgpq83JlY5Dqk4l94SraXDezOeyxjtqA+DTyA3xTdWvYpk1lFfqpKhHWZzeH8gTv02yPErpx+OYCuXXjZz8jis9KEtCTyBPuA79GoQVadDtaicl7/BvYT5U4/sVVjvW4XLEomeL68rJDC/h6NThlezw+NEJ9yUMT6R5Q3gbsQxfpCQhZ8SdtyeW4UlJj3zylohcVNbAFV9V/Ezf4YInA1dDrOPbHZHWk9kpjW1BZdZUyQOXHI+ZFe07eHRG4/MRRLqcgIetllqmuJmOrOYIz+3IXTMVbwfqI4T50DUos8ggssUMNFsTFcFzHc/jI19BD9+Ty7hmR8Sa12fCVSpVAT+X8eKNj7WNwXwZ23OleKV1E21g1LE3f0Q7XWZxSUWsN2DXILVjvMcno6SIfSqf/Z/p3z1eTl/CPkm/Gh/mPuExVqz7hazs880cqOcf8+cZ2fUO2dijNq9Bu7FZY3QaYuJn2jzt71q5xhxDtjUN2Uiz/Z2MG7oPUC8b6CkNMGt6qn+MXEEaoutmxnk8x0FcdQ26WPFynD0lO7zEYPZyWJXAEx12j8AtasN1Hfv+JdzOMU0eqWkFPXiwAWbXtKF6nhmpXghd+bDr+aDiBeRTM3v4gKCXOcYbYft//ICgjzu0BeJ7n2NXI7it3vfvwqMfDy/p3w9OI75nb3cdiejuwm4lJzL4F3bSk2luKzmx4Pc+L/zA9r5Oy6867+uza+F93XbvCc8rG8XLZbfSvCBS/GEdscT3tOsaQ+QuC1Zml3BHvQrWJ9kTPGb9NpHG/kue9ipmMrEnvkY/4ITo6sXek9+if1Wv5FhWJr/HZXiL1Gfeob/6DvvlN+gvv79/efZgQvLcRQbu23dnTm50axqGyjdvI541mppdm3XUTDBnpVXmOasZneMZ/xUzZjfmfJ26aL5uhhkrwv7fs4UX4jlXGRKBun6u8oV5phTmPej3UCnA8JmZ0je+OE9bUFBQUFBQUPCv4gc9WiRPrOkbtgAAAABJRU5ErkJggg=="      alt="mdo" width="32" height="32" class="rounded-circle"></div><div class="col">' +
        ' <p class="text-left"><b> Flavio Urdiales Mena' + '</b></p></div></div></div>' +
        '<textarea id="txtnota" class="form-control mt-2" placeholder="Alguna nueva noticia?" style="border: none; border-bottom: 1px solid #ced4da; border-radius: 0px;"></textarea>',
      confirmButtonText: 'Publicar',
      confirmButtonColor: '#b20303',
      focusConfirm: false,
      preConfirm: () => {
        const nota = (<HTMLInputElement>document.getElementById('txtnota')).value;
        if (!nota) {
          Swal.showValidationMessage(`Ingrese una noticia`)
        }
      }
    }).then((result) => {
      if (result.isConfirmed) {


        let data = {
          "idPersonal": this.usuario.idPersonal,
          "nota": this.bloquearXSS((<HTMLInputElement>document.getElementById('txtnota')).value)
        }
        axios.post(url, data).then(
          response => {
            Swal.fire({
              title: 'Noticia publicada',
              icon: 'success',
              confirmButtonText: 'Ok',
              confirmButtonColor: '#b20303',
              timer: 1500
            }).then((result) => {
              this.getNoticias();
            })
          }
        ).catch(
          error => {
          }
        )
      }
    })


  }


  comentarNota = async (id: any) => {
    let url = this.urlservices + "Noticias/comentario";

    if (this.usuario.idUsuario == null || this.usuario.idUsuario == "" || this.usuario.idUsuario == undefined) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Inicia sesion para comentar',
        confirmButtonColor: '#b20303',
      })
      window.location.href = "/login";
      return;
    }

    if ((<HTMLInputElement>document.getElementById('comentario' + id)).value == "") {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'No puedes comentar un campo vacio',
        confirmButtonColor: '#b20303',
      })
      return;
    }

    let data = {
      "idUsuario": this.usuario.idUsuario,
      "idNota": id,
      "comentario": this.bloquearXSS((<HTMLInputElement>document.getElementById('comentario' + id)).value)
    }

    await axios.post(url, data).then(async response => {

      this.noticias.forEach((element: any) => {
        if (element.identificador == id) {
          element.comentarios.push(response.data);
        }
      }
      );

      (<HTMLInputElement>document.getElementById('comentario' + id)).value = "";
    }
    ).catch(
      error => {
        console.log(error);
      }
    )

  }


  responderComentario = async (id_nota: any, id_comentario: any) => {

    let url = this.urlservices + "Noticias/respuesta";


    if (this.usuario.idUsuario == null || this.usuario.idUsuario == "" || this.usuario.idUsuario == undefined) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Inicia sesion para comentar',
        confirmButtonColor: '#b20303',
      })
      window.location.href = "/login";
      return;
    }


    if ((<HTMLInputElement>document.getElementById('respuesta' + id_nota + "comentario" + id_comentario)).value == "") {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'No puedes responder un campo vacio',
        confirmButtonColor: '#b20303',
      })
      return;
    }
    let data = {
      "idUsuario": this.usuario.idUsuario,
      "idComentario": id_comentario,
      "respuesta": this.bloquearXSS((<HTMLInputElement>document.getElementById('respuesta' + id_nota + "comentario" + id_comentario)).value)
    }


    await axios.post(url, data).then(async response => {

      this.noticias.forEach((element: any) => {
        if (element.identificador == id_nota) {
          element.comentarios.forEach((comentario: any) => {
            if (comentario.identificador == id_comentario) {
              comentario.respuestas.push(response.data);
            }
          }
          );
        }
      }
      );

      (<HTMLInputElement>document.getElementById('respuesta' + id_nota + "comentario" + id_comentario)).value = "";
    }
    ).catch(
      error => {
        console.log(error);
      }
    )

  }


}
