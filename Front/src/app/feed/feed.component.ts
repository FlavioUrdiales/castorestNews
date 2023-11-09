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


  comentar = (id: any) => {

    let divname = "comentarios" + id;
    const comments = document.getElementById(divname);

    if (comments != null) {

      if (comments.hidden == true) {
        comments.hidden = false;
      } else {
        comments.hidden = true;
      }

    }

  }

  close = (id: any) => {

    let divname = "comentarios" + id;
    const comments = document.getElementById(divname);
    if (comments != null) {

      comments.hidden = true;

    }

  }


  responder = (id_comentario: any, id_respuesta: any) => {


    let divname = "respuestasnota" + id_respuesta + "comentario" + id_comentario;
    const comments = document.getElementById(divname);
    if (comments != null) {

      if (comments.hidden == true) {
        comments.hidden = false;
      } else {
        comments.hidden = true;
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
      html: '<div class="container"><div class="row"><div class="col-auto"><img src="https://scontent-qro1-2.xx.fbcdn.net/v/t39.30808-1/356819733_1328765421073458_5159895953506304381_n.jpg?stp=dst-jpg_p200x200&_nc_cat=106&ccb=1-7&_nc_sid=5f2048&_nc_ohc=ap2yD2jh6CQAX9wHWW_&_nc_ht=scontent-qro1-2.xx&oh=00_AfDzyU2ggeSucXiaiigqdZKK7WyPghlfayp-P-zElktm9g&oe=65521C29" alt="mdo" width="32" height="32" class="rounded-circle"></div><div class="col">' +
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
